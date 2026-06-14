package com.estoque.pedidos.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.estoque.pedidos.dto.request.ItemPedidoRequestDTO;
import com.estoque.pedidos.dto.response.ItemPedidoResponseDTO;
import com.estoque.pedidos.service.ItemPedidoService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/itens")
public class ItemPedidoController {

    private final ItemPedidoService service;

    public ItemPedidoController(ItemPedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ItemPedidoResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ItemPedidoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemPedidoResponseDTO salvar(@Valid @RequestBody ItemPedidoRequestDTO itemPedidoDTO) {
        return service.save(itemPedidoDTO);
    }

    @PutMapping("/{id}")
    public ItemPedidoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ItemPedidoRequestDTO itemPedidoDTO) {
        return service.update(id, itemPedidoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}