package com.estoque.pedidos.dto.response;

import java.time.LocalDate;
import java.util.List;

public record PedidoResponseDTO(
        Long idPedido,
        LocalDate dataPedido,
        String status,
        Double valorTotal,
        ClienteResponseDTO cliente,
        List<ItemPedidoResponseDTO> itens // lista aglutinada
) {
}