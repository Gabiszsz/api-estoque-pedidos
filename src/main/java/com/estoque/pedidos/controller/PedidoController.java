package com.estoque.pedidos.controller;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel; // Importado
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.estoque.pedidos.dto.request.PedidoRequestDTO;
import com.estoque.pedidos.dto.response.PedidoResponseDTO;
import com.estoque.pedidos.service.PedidoService;
import com.estoque.pedidos.assembler.PedidoModelAssembler; // Importado
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoModelAssembler assembler;

    public PedidoController(PedidoService service, PedidoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public List<EntityModel<PedidoResponseDTO>> buscarTodos() {
        return service.findAll().stream()
                .map(assembler::toModel) // Envolve o DTO com HATEOAS
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EntityModel<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<PedidoResponseDTO> salvar(@Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        return assembler.toModel(service.save(pedidoDTO));
    }

    @PutMapping("/{id}")
    public EntityModel<PedidoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        return assembler.toModel(service.update(id, pedidoDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}