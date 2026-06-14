package com.estoque.pedidos.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.estoque.pedidos.dto.request.PedidoRequestDTO;
import com.estoque.pedidos.dto.response.PedidoResponseDTO;
import com.estoque.pedidos.service.PedidoService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PedidoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO salvar(@Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        return service.save(pedidoDTO);
    }

    @PutMapping("/{id}")
    public PedidoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        return service.update(id, pedidoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}