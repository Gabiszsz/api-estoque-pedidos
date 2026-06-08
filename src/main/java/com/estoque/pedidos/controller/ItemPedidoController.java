package com.estoque.pedidos.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.estoque.pedidos.dto.request.ItemPedidoRequestDTO;
import com.estoque.pedidos.dto.response.ItemPedidoResponseDTO;
import com.estoque.pedidos.service.ItemPedidoService;

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
    public ItemPedidoResponseDTO salvar(@Valid @RequestBody ItemPedidoRequestDTO itemPedidoDTO) {
        return service.save(itemPedidoDTO);
    }

    @PutMapping("/{id}")
    public ItemPedidoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ItemPedidoRequestDTO itemPedidoDTO) {
        return service.update(id, itemPedidoDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}