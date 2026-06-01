package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.dto.request.ClienteRequestDTO;
import com.estoque.pedidos.dto.response.ClienteResponseDTO;
import com.estoque.pedidos.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<ClienteResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
        return converteParaResponseDTO(cliente);
    }

    public ClienteResponseDTO save(ClienteRequestDTO requestDTO) {
        Cliente cliente = new Cliente();
        cliente.setCpf(requestDTO.cpf());
        cliente.setEnderecoCompleto(requestDTO.enderecoCompleto());

        Cliente clienteSalvo = repository.save(cliente);
        return converteParaResponseDTO(clienteSalvo);
    }

    public ClienteResponseDTO update(Long id, ClienteRequestDTO requestDTO) {
        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));

        clienteExistente.setCpf(requestDTO.cpf());
        clienteExistente.setEnderecoCompleto(requestDTO.enderecoCompleto());

        Cliente clienteAtualizado = repository.save(clienteExistente);
        return converteParaResponseDTO(clienteAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private ClienteResponseDTO converteParaResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(cliente.getId(), cliente.getCpf(), cliente.getEnderecoCompleto());
    }
}