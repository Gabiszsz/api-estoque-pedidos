package com.estoque.pedidos.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estoque.pedidos.dto.request.CategoriaRequestDTO;
import com.estoque.pedidos.dto.response.CategoriaResponseDTO;
import com.estoque.pedidos.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public CollectionModel<CategoriaResponseDTO> buscarTodos() {
        List<CategoriaResponseDTO> categorias = service.findAll();
        return CollectionModel.of(categorias, 
                linkTo(methodOn(CategoriaController.class).buscarTodos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        CategoriaResponseDTO dto = service.findById(id);
        return EntityModel.of(dto);
    }

    @PostMapping
    public EntityModel<CategoriaResponseDTO> salvar(@Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        CategoriaResponseDTO dto = service.save(categoriaDTO);
        return EntityModel.of(dto);
    }

    @PutMapping("/{id}")
    public EntityModel<CategoriaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        CategoriaResponseDTO dto = service.update(id, categoriaDTO);
        return EntityModel.of(dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}