package com.estoque.pedidos.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProdutoResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ProdutoResponseDTO salvar(@RequestBody ProdutoRequestDTO produtoDTO) {
        return service.save(produtoDTO);
    }

    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizar(@PathVariable Long id, @RequestBody ProdutoRequestDTO produtoDTO) {
        return service.update(id, produtoDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}