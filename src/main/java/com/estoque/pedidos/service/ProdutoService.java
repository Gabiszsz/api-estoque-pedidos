package com.estoque.pedidos.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.model.Categoria;
import com.estoque.pedidos.model.vo.Preco;
import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.repository.ProdutoRepository;
import com.estoque.pedidos.repository.CategoriaRepository;
import com.estoque.pedidos.mapper.ProdutoMapper;
import com.estoque.pedidos.exception.RegraNegocioException;
import com.estoque.pedidos.exception.ResourceNotFoundException;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository repository, ProdutoMapper mapper, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoriaRepository = categoriaRepository;
    }

    @Cacheable(value = "listaProdutos")
    public List<ProdutoResponseDTO> findAll() {
        return repository.findAll().stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "produtoUnico", key = "#id")
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
        return mapper.toResponseDTO(produto);
    }

    @CacheEvict(value = "listaProdutos", allEntries = true)
    public ProdutoResponseDTO save(ProdutoRequestDTO requestDTO) {
        if (repository.existsBySku(requestDTO.sku())) {
            throw new RegraNegocioException("O SKU informado já está cadastrado em outro produto.");
        }

        Categoria categoria = categoriaRepository.findById(requestDTO.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + requestDTO.categoriaId()));

        Produto produto = mapper.toEntity(requestDTO);
        produto.setCategoria(categoria);

        Produto produtoSalvo = repository.save(produto);
        return mapper.toResponseDTO(produtoSalvo);
    }

    @Caching(evict = {
            @CacheEvict(value = "produtoUnico", key = "#id"),
            @CacheEvict(value = "listaProdutos", allEntries = true) })
    public ProdutoResponseDTO update(Long id, ProdutoRequestDTO requestDTO) {
        Produto produtoExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));

        if (!produtoExistente.getSku().equals(requestDTO.sku()) && repository.existsBySku(requestDTO.sku())) {
            throw new RegraNegocioException("O SKU informado já está sendo utilizado por outro produto.");
        }

        Categoria categoria = categoriaRepository.findById(requestDTO.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + requestDTO.categoriaId()));

        mapper.updateEntityFromDTO(requestDTO, produtoExistente);
        produtoExistente.setCategoria(categoria);

        Produto produtoAtualizado = repository.save(produtoExistente);
        return mapper.toResponseDTO(produtoAtualizado);
    }

    @Caching(evict = {
            @CacheEvict(value = "produtoUnico", key = "#id"),
            @CacheEvict(value = "listaProdutos", allEntries = true) })
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Não é possível deletar. Produto não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "produtoUnico", key = "#id"),
            @CacheEvict(value = "listaProdutos", allEntries = true) })
    public ProdutoResponseDTO atualizarPreco(Long id, BigDecimal novoPreco) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));

        produto.setPreco(new Preco(novoPreco, "BRL"));

        Produto produtoSalvo = repository.save(produto);
        return mapper.toResponseDTO(produtoSalvo);
    }
}