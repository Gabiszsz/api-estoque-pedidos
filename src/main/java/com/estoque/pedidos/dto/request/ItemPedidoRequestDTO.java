package com.estoque.pedidos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ItemPedidoRequestDTO(
        @NotNull(message = "A quantidade é obrigatória.")
        @Positive(message = "A quantidade deve ser maior do que zero.")
        Integer quantidade,

        @NotNull(message = "O preço unitário é obrigatório.")
        @PositiveOrZero(message = "O preço unitário não pode ser negativo.")
        Double precoUnitario,

        @NotNull(message = "O ID do pedido é obrigatório.")
        Long pedidoId,

        @NotNull(message = "O ID do produto é obrigatório.")
        Long produtoId
) {
}