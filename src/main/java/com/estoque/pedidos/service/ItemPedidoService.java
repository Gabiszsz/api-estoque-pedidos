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
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemPedido não encontrado com o ID: " + id));
    }

    public ItemPedido save(ItemPedido itemPedido) {
        return repository.save(itemPedido);
    }

    public ItemPedido update(Long id, ItemPedido itemAtualizado) {
        ItemPedido itemExistente = findById(id);

        itemExistente.setQuantidade(itemAtualizado.getQuantidade());
        itemExistente.setPrecoUnitario(itemAtualizado.getPrecoUnitario());
        itemExistente.setPedido(itemAtualizado.getPedido());
        itemExistente.setProduto(itemAtualizado.getProduto());

        return repository.save(itemExistente);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("ItemPedido não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}