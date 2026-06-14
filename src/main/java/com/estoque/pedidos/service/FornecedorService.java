package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Fornecedor;
import com.estoque.pedidos.dto.request.FornecedorRequestDTO;
import com.estoque.pedidos.dto.response.FornecedorResponseDTO;
import com.estoque.pedidos.repository.FornecedorRepository;
import com.estoque.pedidos.mapper.FornecedorMapper;
import com.estoque.pedidos.exception.ResourceNotFoundException;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;
    private final FornecedorMapper mapper;

    public FornecedorService(FornecedorRepository repository, FornecedorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<FornecedorResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public FornecedorResponseDTO findById(Long id) {
        Fornecedor fornecedor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com o ID: " + id));
        return mapper.toResponseDTO(fornecedor);
    }

    public FornecedorResponseDTO save(FornecedorRequestDTO requestDTO) {
        Fornecedor fornecedor = mapper.toEntity(requestDTO);
        Fornecedor fornecedorSalvo = repository.save(fornecedor);
        return mapper.toResponseDTO(fornecedorSalvo);
    }

    public FornecedorResponseDTO update(Long id, FornecedorRequestDTO requestDTO) {
        Fornecedor fornecedorExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com o ID: " + id));

        mapper.updateEntityFromDTO(requestDTO, fornecedorExistente);

        Fornecedor fornecedorAtualizado = repository.save(fornecedorExistente);
        return mapper.toResponseDTO(fornecedorAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Fornecedor não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}