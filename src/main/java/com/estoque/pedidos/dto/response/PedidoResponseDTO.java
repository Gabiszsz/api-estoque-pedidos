package com.estoque.pedidos.dto.response;

import java.time.LocalDate;

public record PedidoResponseDTO(
        Long idPedido,
        LocalDate dataPedido,
        String status,
        Double valorTotal,
        ClienteResponseDTO cliente
) {

}