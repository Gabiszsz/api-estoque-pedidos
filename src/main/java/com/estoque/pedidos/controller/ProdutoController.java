package com.estoque.pedidos.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public CollectionModel<ProdutoResponseDTO> buscarTodos() {
        List<ProdutoResponseDTO> produtos = service.findAll();
        return CollectionModel.of(produtos, 
                linkTo(ProdutoController.class).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return EntityModel.of(service.findById(id));
    }

    @PostMapping
    public EntityModel<ProdutoResponseDTO> salvar(@Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        return EntityModel.of(service.save(produtoDTO));
    }

    @PutMapping("/{id}")
    public EntityModel<ProdutoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        return EntityModel.of(service.update(id, produtoDTO));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}