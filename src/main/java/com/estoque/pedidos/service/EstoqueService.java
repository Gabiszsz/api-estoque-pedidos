package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Estoque;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.EstoqueRequestDTO;
import com.estoque.pedidos.dto.response.EstoqueResponseDTO;
import com.estoque.pedidos.repository.EstoqueRepository;
import com.estoque.pedidos.repository.ProdutoRepository;
import com.estoque.pedidos.mapper.EstoqueMapper;
import com.estoque.pedidos.exception.ResourceNotFoundException;

@Service
public class EstoqueService {

    private final EstoqueRepository repository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueMapper mapper;

    public EstoqueService(EstoqueRepository repository, ProdutoRepository produtoRepository, EstoqueMapper mapper) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.mapper = mapper;
    }

    public List<EstoqueResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EstoqueResponseDTO findById(Long id) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado com o ID: " + id));
        return mapper.toResponseDTO(estoque);
    }

    public EstoqueResponseDTO save(EstoqueRequestDTO requestDTO) {
        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + requestDTO.produtoId()));

        Estoque estoque = mapper.toEntity(requestDTO);
        estoque.setProduto(produto);

        Estoque estoqueSalvo = repository.save(estoque);
        return mapper.toResponseDTO(estoqueSalvo);
    }

    public EstoqueResponseDTO update(Long id, EstoqueRequestDTO requestDTO) {
        Estoque estoqueExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado com o ID: " + id));

        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + requestDTO.produtoId()));

        mapper.updateEntityFromDTO(requestDTO, estoqueExistente);
        estoqueExistente.setProduto(produto);

        Estoque estoqueAtualizado = repository.save(estoqueExistente);
        return mapper.toResponseDTO(estoqueAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Estoque não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}