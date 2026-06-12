package com.estoque.pedidos.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO(
        @NotNull(message = "A data do pedido é obrigatória.")
        LocalDate dataPedido,

        @NotBlank(message = "O status do pedido é obrigatório.")
        String status,

        @NotNull(message = "O ID do cliente é obrigatório.")
        Long clienteId
) {
}