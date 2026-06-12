package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.stereotype.Service;

import com.estoque.pedidos.controller.ProdutoController;
import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "produtos")
    public List<ProdutoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "produto", key = "#id")
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
        return converteParaResponseDTO(produto);
    }

    @CacheEvict(value = "produtos", allEntries = true)
    public ProdutoResponseDTO save(ProdutoRequestDTO requestDTO) {
        if (requestDTO.precoVenda() < 0) {
            throw new RuntimeException("O preço não pode ser negativo.");
        }
        if (requestDTO.nome() == null || requestDTO.nome().isBlank()) {
            throw new RuntimeException("O nome do produto é obrigatório.");
        }
        if (repository.existsBySku(requestDTO.sku())) {
            throw new RuntimeException("Já existe um produto com este SKU.");
        }

        Produto produto = converteParaEntidade(requestDTO);
        Produto produtoSalvo = repository.save(produto);

        return converteParaResponseDTO(produtoSalvo);
    }

    @Caching(evict = {
        @CacheEvict(value = "produto", key = "#id"),
        @CacheEvict(value = "produtos", allEntries = true)
    })
    public ProdutoResponseDTO update(Long id, ProdutoRequestDTO requestDTO) {
        Produto produtoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));

        if (requestDTO.precoVenda() < 0) {
            throw new RuntimeException("O preço não pode ser negativo.");
        }
        if (requestDTO.nome() == null || requestDTO.nome().isBlank()) {
            throw new RuntimeException("O nome do produto é obrigatório.");
        }
        if (repository.existsBySkuAndIdNot(requestDTO.sku(), id)) {
            throw new RuntimeException("Já existe um produto com esse SKU.");
        }

        produtoExistente.setSku(requestDTO.sku());
        produtoExistente.setNome(requestDTO.nome());
        produtoExistente.setPrecoVenda(requestDTO.precoVenda());
        produtoExistente.setUnidadeMedida(requestDTO.unidadeMedida());
        produtoExistente.setQuantidadeEstoque(requestDTO.quantidadeEstoque());

        Produto produtoAtualizado = repository.save(produtoExistente);

        return converteParaResponseDTO(produtoAtualizado);
    }

    @Caching(evict = {
            @CacheEvict(value = "produtos", allEntries = true),
            @CacheEvict(value = "produto", key = "#id")
    })
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar. Produto não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private Produto converteParaEntidade(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setSku(dto.sku());
        produto.setNome(dto.nome());
        produto.setPrecoVenda(dto.precoVenda());
        produto.setUnidadeMedida(dto.unidadeMedida());
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());
        return produto;
    }

    private ProdutoResponseDTO converteParaResponseDTO(Produto produto) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPrecoVenda(),
                produto.getQuantidadeEstoque());

        // LINKS padrão do slide do seu professor usando .slash()
        dto.add(linkTo(ProdutoController.class).slash(produto.getId()).withSelfRel());
        dto.add(linkTo(ProdutoController.class).withRel("lista_produtos"));
        dto.add(linkTo(ProdutoController.class).slash(produto.getId()).withRel("deletar"));

        return dto;
    }
}