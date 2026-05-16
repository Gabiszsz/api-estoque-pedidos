package com.estoque.pedidos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estoque.pedidos.model.ItemPedido;
import com.estoque.pedidos.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repository;

    public ItemPedidoService(ItemPedidoRepository repository) {
        this.repository = repository;
    }

    public List<ItemPedido> findAll() {
        return repository.findAll();
    }

    public ItemPedido findById(Long id) {
        return repository.findById(id);
    }

    public ItemPedido save(ItemPedido itemPedido) {
        return repository.save(itemPedido);
    }

    public ItemPedido update(Long id, ItemPedido itemPedido) {
        return repository.update(id, itemPedido);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
