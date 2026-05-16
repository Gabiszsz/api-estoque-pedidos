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

import com.estoque.pedidos.model.Admin;
import com.estoque.pedidos.service.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService service;

    public AdminController(
        AdminService service
    ) {
        this.service = service;
    }

    @GetMapping
    public List<Admin> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Admin buscarPorId(
        @PathVariable Long id
    ) {
        return service.findById(id);
    }

    @PostMapping
    public Admin salvar(
        @RequestBody Admin admin
    ) {
        return service.save(admin);
    }

    @PutMapping("/{id}")
    public Admin atualizar(
        @PathVariable Long id,
        @RequestBody Admin admin
    ) {
        return service.update(id, admin);
    }

    @DeleteMapping("/{id}")
    public void deletar(
        @PathVariable Long id
    ) {
        service.delete(id);
    }
}
