package com.estoque.pedidos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<Pedido> findAll() {
        return repository.findAll();
    }

    public Pedido findById(Long id) {
        return repository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }

    public Pedido update(Long id, Pedido pedido) {
        return repository.update(id, pedido);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}