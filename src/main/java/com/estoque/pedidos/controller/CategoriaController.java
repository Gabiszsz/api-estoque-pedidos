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

import com.estoque.pedidos.model.Categoria;
import com.estoque.pedidos.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(
        CategoriaService service
    ) {
        this.service = service;
    }

    @GetMapping
    public List<Categoria> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Categoria buscarPorId(
        @PathVariable Long id
    ) {
        return service.findById(id);
    }

    @PostMapping
    public Categoria salvar(
        @RequestBody Categoria categoria
    ) {
        return service.save(categoria);
    }

    @PutMapping("/{id}")
    public Categoria atualizar(
        @PathVariable Long id,
        @RequestBody Categoria categoria
    ) {
        return service.update(id, categoria);
    }

    @DeleteMapping("/{id}")
    public void deletar(
        @PathVariable Long id
    ) {
        service.delete(id);
    }
}
