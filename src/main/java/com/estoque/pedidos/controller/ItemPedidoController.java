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

import com.estoque.pedidos.model.ItemPedido;
import com.estoque.pedidos.service.ItemPedidoService;

@RestController
@RequestMapping("/itens")
public class ItemPedidoController {

    private final ItemPedidoService service;

    public ItemPedidoController(ItemPedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ItemPedido> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ItemPedido buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ItemPedido salvar(@RequestBody ItemPedido itemPedido) {
        return service.save(itemPedido);
    }

    @PutMapping("/{id}")
    public ItemPedido atualizar(
        @PathVariable Long id,
        @RequestBody ItemPedido itemPedido
    ) {
        return service.update(id, itemPedido);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}
