package com.estoque.pedidos.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.estoque.pedidos.dto.request.EstoqueRequestDTO;
import com.estoque.pedidos.dto.response.EstoqueResponseDTO;
import com.estoque.pedidos.service.EstoqueService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    @GetMapping
    public List<EstoqueResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public EstoqueResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstoqueResponseDTO salvar(@Valid @RequestBody EstoqueRequestDTO estoqueDTO) {
        return service.save(estoqueDTO);
    }

    @PutMapping("/{id}")
    public EstoqueResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody EstoqueRequestDTO estoqueDTO) {
        return service.update(id, estoqueDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}