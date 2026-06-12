package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.stereotype.Service;

import com.estoque.pedidos.controller.ClienteController;
import com.estoque.pedidos.dto.request.ClienteRequestDTO;
import com.estoque.pedidos.dto.response.ClienteResponseDTO;
import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "clientes")
    public List<ClienteResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "cliente", key = "#id")
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
        return converteParaResponseDTO(cliente);
    }

    @CacheEvict(value = "clientes", allEntries = true)
    public ClienteResponseDTO save(ClienteRequestDTO requestDTO) {
        if (repository.existsByCpf(requestDTO.cpf())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este CPF.");
        }

        Cliente cliente = new Cliente();
        cliente.setCpf(requestDTO.cpf());
        cliente.setEnderecoCompleto(requestDTO.enderecoCompleto());

        Cliente clienteSalvo = repository.save(cliente);
        return converteParaResponseDTO(clienteSalvo);
    }

    @Caching(evict = {
        @CacheEvict(value = "cliente", key = "#id"),
        @CacheEvict(value = "clientes", allEntries = true)
    })
    public ClienteResponseDTO update(Long id, ClienteRequestDTO requestDTO) {
        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));

        if (repository.existsByCpfAndIdNot(requestDTO.cpf(), id)) {
            throw new RuntimeException("Este CPF já está sendo utilizado por outro cliente.");
        }

        clienteExistente.setCpf(requestDTO.cpf());
        clienteExistente.setEnderecoCompleto(requestDTO.enderecoCompleto());

        Cliente clienteAtualizado = repository.save(clienteExistente);
        return converteParaResponseDTO(clienteAtualizado);
    }

    @Caching(evict = {
            @CacheEvict(value = "clientes", allEntries = true),
            @CacheEvict(value = "cliente", key = "#id")
    })
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private ClienteResponseDTO converteParaResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO(cliente.getId(), cliente.getCpf(), cliente.getEnderecoCompleto());

        // LINKS HIpermédia no padrão exato do slide do professor usando .slash()
        dto.add(linkTo(ClienteController.class).slash(cliente.getId()).withSelfRel());
        dto.add(linkTo(ClienteController.class).withRel("lista_clientes"));
        dto.add(linkTo(ClienteController.class).slash(cliente.getId()).withRel("deletar"));

        return dto;
    }
}