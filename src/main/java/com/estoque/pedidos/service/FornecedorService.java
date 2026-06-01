package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Fornecedor;
import com.estoque.pedidos.dto.request.FornecedorRequestDTO;
import com.estoque.pedidos.dto.response.FornecedorResponseDTO;
import com.estoque.pedidos.repository.FornecedorRepository;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    public List<FornecedorResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    public FornecedorResponseDTO findById(Long id) {
        Fornecedor fornecedor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com o ID: " + id));
        return converteParaResponseDTO(fornecedor);
    }

    public FornecedorResponseDTO save(FornecedorRequestDTO requestDTO) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCnpj(requestDTO.cnpj());
        fornecedor.setRazaoSocial(requestDTO.razaoSocial());
        fornecedor.setContatoVendedor(requestDTO.contatoVendedor());

        Fornecedor fornecedorSalvo = repository.save(fornecedor);
        return converteParaResponseDTO(fornecedorSalvo);
    }

    public FornecedorResponseDTO update(Long id, FornecedorRequestDTO requestDTO) {
        Fornecedor fornecedorExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com o ID: " + id));

        fornecedorExistente.setCnpj(requestDTO.cnpj());
        fornecedorExistente.setRazaoSocial(requestDTO.razaoSocial());
        fornecedorExistente.setContatoVendedor(requestDTO.contatoVendedor());

        Fornecedor fornecedorAtualizado = repository.save(fornecedorExistente);
        return converteParaResponseDTO(fornecedorAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Fornecedor não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private FornecedorResponseDTO converteParaResponseDTO(Fornecedor fornecedor) {
        return new FornecedorResponseDTO(
                fornecedor.getId(),
                fornecedor.getCnpj(),
                fornecedor.getRazaoSocial(),
                fornecedor.getContatoVendedor()
        );
    }
}