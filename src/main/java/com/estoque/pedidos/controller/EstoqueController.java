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

import com.estoque.pedidos.dto.request.EstoqueRequestDTO;
import com.estoque.pedidos.dto.response.EstoqueResponseDTO;
import com.estoque.pedidos.service.EstoqueService;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    // CORRIGIDO: Alinhado para retornar List<EstoqueResponseDTO>
    @GetMapping
    public List<EstoqueResponseDTO> buscarTodos() {
        return service.findAll();
    }

    // CORRIGIDO: Alinhado para retornar EstoqueResponseDTO
    @GetMapping("/{id}")
    public EstoqueResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    // CORRIGIDO: Recebe RequestDTO e retorna ResponseDTO
    @PostMapping
    public EstoqueResponseDTO salvar(@RequestBody EstoqueRequestDTO estoqueDTO) {
        return service.save(estoqueDTO);
    }

    // CORRIGIDO: Recebe RequestDTO e retorna ResponseDTO
    @PutMapping("/{id}")
    public EstoqueResponseDTO atualizar(@PathVariable Long id, @RequestBody EstoqueRequestDTO estoqueDTO) {
        return service.update(id, estoqueDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}