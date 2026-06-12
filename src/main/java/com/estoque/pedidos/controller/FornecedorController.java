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

import com.estoque.pedidos.dto.request.FornecedorRequestDTO;
import com.estoque.pedidos.dto.response.FornecedorResponseDTO;
import com.estoque.pedidos.service.FornecedorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }

    @GetMapping
    public CollectionModel<FornecedorResponseDTO> buscarTodos() {
        List<FornecedorResponseDTO> fornecedores = service.findAll();
        return CollectionModel.of(fornecedores, 
                linkTo(FornecedorController.class).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<FornecedorResponseDTO> buscarPorId(@PathVariable Long id) {
        return EntityModel.of(service.findById(id));
    }

    @PostMapping
    public EntityModel<FornecedorResponseDTO> salvar(@Valid @RequestBody FornecedorRequestDTO fornecedorDTO) {
        return EntityModel.of(service.save(fornecedorDTO));
    }

    @PutMapping("/{id}")
    public EntityModel<FornecedorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FornecedorRequestDTO fornecedorDTO) {
        return EntityModel.of(service.update(id, fornecedorDTO));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}