package com.estoque.pedidos.dto.request;

public record ItemPedidoRequestDTO(
        Integer quantidade,
        Double precoUnitario,
        Long pedidoId,
        Long produtoId
) {

}