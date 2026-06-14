package com.estoque.pedidos.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.estoque.pedidos.assembler.ProdutoModelAssembler;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.service.ProdutoService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;
    private final ProdutoModelAssembler assembler; // Injetar o assembler

    public ProdutoController(ProdutoService service, ProdutoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public List<EntityModel<ProdutoResponseDTO>> buscarTodos() {
        return service.findAll().stream()
                .map(assembler::toModel) // Transforma em EntityModel
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EntityModel<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<ProdutoResponseDTO> salvar(@Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        return assembler.toModel(service.save(produtoDTO));
    }

    @PutMapping("/{id}")
    public EntityModel<ProdutoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        return assembler.toModel(service.update(id, produtoDTO));
    }

    // O delete continua a retornar void/204, pois não há "modelo" para representar após a deleção
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletar(@PathVariable Long id) { // Alterado para ResponseEntity<Void>
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}