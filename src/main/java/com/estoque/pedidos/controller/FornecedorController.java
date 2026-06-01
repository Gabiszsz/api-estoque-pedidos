package com.estoque.pedidos.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.estoque.pedidos.dto.request.FornecedorRequestDTO;
import com.estoque.pedidos.dto.response.FornecedorResponseDTO;
import com.estoque.pedidos.service.FornecedorService;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }

    @GetMapping
    public List<FornecedorResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public FornecedorResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public FornecedorResponseDTO salvar(@RequestBody FornecedorRequestDTO fornecedorDTO) {
        return service.save(fornecedorDTO);
    }

    @PutMapping("/{id}")
    public FornecedorResponseDTO atualizar(@PathVariable Long id, @RequestBody FornecedorRequestDTO fornecedorDTO) {
        return service.update(id, fornecedorDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}