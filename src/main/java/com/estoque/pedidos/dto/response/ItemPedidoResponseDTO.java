package com.estoque.pedidos.dto.response;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long id,
        Integer quantidade,
        BigDecimal precoUnitario,
        Long pedidoId,
        Long produtoId
) {}