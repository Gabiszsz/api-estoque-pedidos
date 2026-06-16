package com.estoque.pedidos.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoResponseDTO(
        Long idPagamento,
        String metodoPagamento,
        LocalDate dataConfirmacao,
        String statusPagamento,
        BigDecimal valorPago,
        Long pedidoId
) {}