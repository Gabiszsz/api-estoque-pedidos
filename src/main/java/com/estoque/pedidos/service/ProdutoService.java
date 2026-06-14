package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.repository.ProdutoRepository;
import com.estoque.pedidos.mapper.ProdutoMapper;
import com.estoque.pedidos.exception.RegraNegocioException;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    public ProdutoService(ProdutoRepository repository, ProdutoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Cacheable(value = "listaProdutos")
    public List<ProdutoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "produtoUnico", key = "#id")
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Produto não encontrado com o ID: " + id));
        return mapper.toResponseDTO(produto);
    }

    @CacheEvict(value = "listaProdutos", allEntries = true)
    public ProdutoResponseDTO save(ProdutoRequestDTO requestDTO) {
        if (repository.existsBySku(requestDTO.sku())) {
            throw new RegraNegocioException("O SKU informado já está cadastrado em outro produto.");
        }

        Produto produto = mapper.toEntity(requestDTO);
        Produto produtoSalvo = repository.save(produto);
        return mapper.toResponseDTO(produtoSalvo);
    }

    @Caching(evict = { // Limpa o produto específico e a lista geral quando atualiza
            @CacheEvict(value = "produtoUnico", key = "#id"),
            @CacheEvict(value = "listaProdutos", allEntries = true)
    })
    public ProdutoResponseDTO update(Long id, ProdutoRequestDTO requestDTO) {
        Produto produtoExistente = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Produto não encontrado com o ID: " + id));
        if (!produtoExistente.getSku().equals(requestDTO.sku()) && repository.existsBySku(requestDTO.sku())) {
            throw new RegraNegocioException("O SKU informado já está sendo utilizado por outro produto.");
        }

        mapper.updateEntityFromDTO(requestDTO, produtoExistente);
        Produto produtoAtualizado = repository.save(produtoExistente);
        return mapper.toResponseDTO(produtoAtualizado);
    }

    @Caching(evict = {
            @CacheEvict(value = "produtoUnico", key = "#id"),
            @CacheEvict(value = "listaProdutos", allEntries = true)
    })
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Não é possível deletar. Produto não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}