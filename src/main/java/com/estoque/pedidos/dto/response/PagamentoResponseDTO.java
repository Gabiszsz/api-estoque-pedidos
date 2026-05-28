package com.estoque.pedidos.dto.response;

import java.time.LocalDate;

public record PagamentoResponseDTO(
        Long idPagamento,
        String metodoPagamento,
        LocalDate dataConfirmacao,
        String statusPagamento,
        Double valorPago,
        Long pedidoId
) {}