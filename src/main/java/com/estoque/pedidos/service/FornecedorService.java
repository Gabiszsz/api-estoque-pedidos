package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.stereotype.Service;

import com.estoque.pedidos.controller.FornecedorController;
import com.estoque.pedidos.dto.request.FornecedorRequestDTO;
import com.estoque.pedidos.dto.response.FornecedorResponseDTO;
import com.estoque.pedidos.model.Fornecedor;
import com.estoque.pedidos.repository.FornecedorRepository;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "fornecedores")
    public List<FornecedorResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "fornecedor", key = "#id")
    public FornecedorResponseDTO findById(Long id) {
        Fornecedor fornecedor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com o ID: " + id));
        return converteParaResponseDTO(fornecedor);
    }

    @CacheEvict(value = "fornecedores", allEntries = true)
    public FornecedorResponseDTO save(FornecedorRequestDTO requestDTO) {
        if (requestDTO.contatoVendedor() == null || requestDTO.contatoVendedor().isBlank()) {
            throw new RuntimeException("Fornecedor deve possuir um contato.");
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCnpj(requestDTO.cnpj());
        fornecedor.setRazaoSocial(requestDTO.razaoSocial());
        fornecedor.setContatoVendedor(requestDTO.contatoVendedor());

        Fornecedor fornecedorSalvo = repository.save(fornecedor);
        return converteParaResponseDTO(fornecedorSalvo);
    }

    @Caching(evict = {
        @CacheEvict(value = "fornecedor", key = "#id"),
        @CacheEvict(value = "fornecedores", allEntries = true)
    })
    public FornecedorResponseDTO update(Long id, FornecedorRequestDTO requestDTO) {
        Fornecedor fornecedorExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com o ID: " + id));

        if (requestDTO.contatoVendedor() == null || requestDTO.contatoVendedor().isBlank()) {
            throw new RuntimeException("Fornecedor deve possuir um contato.");
        }

        fornecedorExistente.setCnpj(requestDTO.cnpj());
        fornecedorExistente.setRazaoSocial(requestDTO.razaoSocial());
        fornecedorExistente.setContatoVendedor(requestDTO.contatoVendedor());

        Fornecedor fornecedorAtualizado = repository.save(fornecedorExistente);
        return converteParaResponseDTO(fornecedorAtualizado);
    }

    @Caching(evict = {
            @CacheEvict(value = "fornecedores", allEntries = true),
            @CacheEvict(value = "fornecedor", key = "#id")
    })
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Fornecedor não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private FornecedorResponseDTO converteParaResponseDTO(Fornecedor fornecedor) {
        FornecedorResponseDTO dto = new FornecedorResponseDTO(
                fornecedor.getId(),
                fornecedor.getCnpj(),
                fornecedor.getRazaoSocial(),
                fornecedor.getContatoVendedor());

        // LINKS HIpermédia no padrão exato do slide do professor
        dto.add(linkTo(FornecedorController.class).slash(fornecedor.getId()).withSelfRel());
        dto.add(linkTo(FornecedorController.class).withRel("lista_fornecedores"));
        dto.add(linkTo(FornecedorController.class).slash(fornecedor.getId()).withRel("deletar"));

        return dto;
    }
}