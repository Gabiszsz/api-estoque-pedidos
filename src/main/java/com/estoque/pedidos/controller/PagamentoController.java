package com.estoque.pedidos.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.estoque.pedidos.dto.request.PagamentoRequestDTO;
import com.estoque.pedidos.dto.response.PagamentoResponseDTO;
import com.estoque.pedidos.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PagamentoResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PagamentoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public PagamentoResponseDTO salvar(@Valid @RequestBody PagamentoRequestDTO pagamentoDTO) {
        return service.save(pagamentoDTO);
    }

    @PutMapping("/{id}")
    public PagamentoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody PagamentoRequestDTO pagamentoDTO) {
        return service.update(id, pagamentoDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}