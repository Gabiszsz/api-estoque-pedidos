package com.estoque.pedidos.dto.response;

import java.time.LocalDate;
import java.util.List;
import com.estoque.pedidos.model.enums.StatusPedido;

public record PedidoResponseDTO(
        Long idPedido,
        LocalDate dataPedido,
        StatusPedido status,
        Double valorTotal,
        ClienteResponseDTO cliente,
        List<ItemPedidoResponseDTO> itens
) {
}