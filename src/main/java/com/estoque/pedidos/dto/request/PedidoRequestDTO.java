package com.estoque.pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

public record PedidoRequestDTO(
        @NotNull(message = "A data do pedido é obrigatória.")
        LocalDate dataPedido,

        @NotBlank(message = "O status do pedido é obrigatório.")
        String status,

        @NotNull(message = "O valor total é obrigatório.")
        @PositiveOrZero(message = "O valor total não pode ser negativo.")
        Double valorTotal,

        @NotNull(message = "O ID do cliente é obrigatório.")
        Long clienteId
) {
}