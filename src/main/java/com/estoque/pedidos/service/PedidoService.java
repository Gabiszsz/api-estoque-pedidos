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
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o ID: " + id));
    }

    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }

    public Pedido update(Long id, Pedido pedidoAtualizado) {
        Pedido pedidoExistente = findById(id);

        pedidoExistente.setDataPedido(pedidoAtualizado.getDataPedido());
        pedidoExistente.setStatus(pedidoAtualizado.getStatus());
        pedidoExistente.setValorTotal(pedidoAtualizado.getValorTotal());
        pedidoExistente.setCliente(pedidoAtualizado.getCliente());

        return repository.save(pedidoExistente);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Pedido não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}