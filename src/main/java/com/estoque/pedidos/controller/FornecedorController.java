package com.estoque.pedidos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estoque.pedidos.model.Fornecedor;
import com.estoque.pedidos.service.FornecedorService;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService service;

    public FornecedorController(
        FornecedorService service
    ) {
        this.service = service;
    }

    @GetMapping
    public List<Fornecedor> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Fornecedor buscarPorId(
        @PathVariable Long id
    ) {
        return service.findById(id);
    }

    @PostMapping
    public Fornecedor salvar(
        @RequestBody Fornecedor fornecedor
    ) {
        return service.save(fornecedor);
    }

    @PutMapping("/{id}")
    public Fornecedor atualizar(
        @PathVariable Long id,
        @RequestBody Fornecedor fornecedor
    ) {
        return service.update(id, fornecedor);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}