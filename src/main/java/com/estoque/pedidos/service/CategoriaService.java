package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.stereotype.Service;

import com.estoque.pedidos.controller.CategoriaController;
import com.estoque.pedidos.dto.request.CategoriaRequestDTO;
import com.estoque.pedidos.dto.response.CategoriaResponseDTO;
import com.estoque.pedidos.model.Categoria;
import com.estoque.pedidos.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "categorias")
    public List<CategoriaResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "categoria", key = "#id")
    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));
        return converteParaResponseDTO(categoria);
    }

    @CacheEvict(value = "categorias", allEntries = true)
    public CategoriaResponseDTO save(CategoriaRequestDTO requestDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(requestDTO.nome());

        Categoria categoriaSalvo = repository.save(categoria);
        return converteParaResponseDTO(categoriaSalvo);
    }

    @Caching(evict = {
        @CacheEvict(value = "categoria", key = "#id"),
        @CacheEvict(value = "categorias", allEntries = true)
    })
    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO requestDTO) {
        Categoria categoriaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));

        categoriaExistente.setNome(requestDTO.nome());

        Categoria categoriaAtualizada = repository.save(categoriaExistente);
        return converteParaResponseDTO(categoriaAtualizada);
    }

    @Caching(evict = {
            @CacheEvict(value = "categorias", allEntries = true),
            @CacheEvict(value = "categoria", key = "#id")
    })
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private CategoriaResponseDTO converteParaResponseDTO(Categoria categoria) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO(categoria.getId(), categoria.getNome());

        // ESTRATÉGIA DO SLIDE: Geração de links usando .slash() para evitar erros
        dto.add(linkTo(CategoriaController.class).slash(categoria.getId()).withSelfRel());
        dto.add(linkTo(CategoriaController.class).withRel("lista_categorias"));
        dto.add(linkTo(CategoriaController.class).slash(categoria.getId()).withRel("deletar"));

        return dto;
    }
}