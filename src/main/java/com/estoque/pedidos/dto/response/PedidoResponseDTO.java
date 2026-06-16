package com.estoque.pedidos.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.estoque.pedidos.model.enums.StatusPedido;

public record PedidoResponseDTO(
        Long idPedido,
        LocalDate dataPedido,
        StatusPedido status,
        BigDecimal valorTotal,
        ClienteResponseDTO cliente,
        List<ItemPedidoResponseDTO> itens
) {}