package com.estoque.pedidos.mother;

import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.model.enums.StatusPedido;

public class PedidoMother {
    public static Pedido criarPedidoAberto() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setStatus(StatusPedido.ABERTO); // Modificado para usar o Enum
        pedido.setValorTotal(100.0);
        return pedido;
    }
}