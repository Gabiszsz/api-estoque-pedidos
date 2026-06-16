package com.estoque.pedidos.mother;

import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.model.enums.StatusPedido;
import java.math.BigDecimal;

public class PedidoMother {
    public static Pedido criarPedidoAberto() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setValorTotal(new BigDecimal("100.00"));
        return pedido;
    }
}