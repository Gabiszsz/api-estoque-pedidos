package com.estoque.pedidos.dto.request;

import java.time.LocalDate;

public record PedidoRequestDTO(
        LocalDate dataPedido,
        String status,
        Double valorTotal,
        Long clienteId
) {

}