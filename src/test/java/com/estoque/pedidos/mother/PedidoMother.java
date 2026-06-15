package com.estoque.pedidos.mother;

import com.estoque.pedidos.model.Pedido;

public class PedidoMother {
    public static Pedido criarPedidoAberto() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setStatus("ABERTO");
        pedido.setValorTotal(100.0);
        return pedido;
    }
}