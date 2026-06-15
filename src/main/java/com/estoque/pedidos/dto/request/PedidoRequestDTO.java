package com.estoque.pedidos.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record PedidoRequestDTO(
        @NotNull(message = "A data do pedido é obrigatória.")
        LocalDate dataPedido,

        @NotNull(message = "O ID do cliente é obrigatório.")
        Long clienteId
) {
}