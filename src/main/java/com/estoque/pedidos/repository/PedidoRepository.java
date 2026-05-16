package com.estoque.pedidos.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component; 

import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.model.Pedido;

@Component 
public class PedidoRepository {

    private final List<Pedido> listaPedidos = new ArrayList<>();

    public PedidoRepository() {
        Cliente cliente1 = new Cliente(1L, "111.111.111-11", "Rua A, número 100");
        Cliente cliente2 = new Cliente(2L, "222.222.222-22", "Rua B, número 200");

        listaPedidos.add(new Pedido(1L, LocalDate.now(), "ABERTO", 4500.0, cliente1));
        listaPedidos.add(new Pedido(2L, LocalDate.now(), "FINALIZADO", 150.0, cliente2));
    }

    public List<Pedido> findAll() {
        return listaPedidos;
    }

    public Pedido findById(Long id) {
        for (Pedido pedido : listaPedidos) {
            if (pedido.getIdPedido().equals(id)) {
                return pedido;
            }
        }
        return null;
    }

    public Pedido save(Pedido pedido) {
        pedido.setIdPedido((long) (listaPedidos.size() + 1));
        listaPedidos.add(pedido);
        return pedido;
    }

    public Pedido update(Long id, Pedido pedidoAtualizado) {
        Pedido pedidoExistente = findById(id);

        if (pedidoExistente != null) {
            pedidoExistente.setDataPedido(pedidoAtualizado.getDataPedido());
            pedidoExistente.setStatus(pedidoAtualizado.getStatus());
            pedidoExistente.setValorTotal(pedidoAtualizado.getValorTotal());
            pedidoExistente.setCliente(pedidoAtualizado.getCliente());

            return pedidoExistente;
        }

        return null;
    }

    public void delete(Long id) {
        listaPedidos.removeIf(pedido -> pedido.getIdPedido().equals(id));
    }
}