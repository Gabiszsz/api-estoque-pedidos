package com.estoque.pedidos.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.estoque.pedidos.dto.request.AdminRequestDTO;
import com.estoque.pedidos.dto.response.AdminResponseDTO;
import com.estoque.pedidos.service.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping
    public List<AdminResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AdminResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public AdminResponseDTO salvar(@Valid @RequestBody AdminRequestDTO adminDTO) {
        return service.save(adminDTO);
    }

    @PutMapping("/{id}")
    public AdminResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody AdminRequestDTO adminDTO) {
        return service.update(id, adminDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}