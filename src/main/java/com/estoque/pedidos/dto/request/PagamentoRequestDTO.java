package com.estoque.pedidos.dto.request;

import java.time.LocalDate;

public record PagamentoRequestDTO(
        String metodoPagamento,
        LocalDate dataConfirmacao,
        String statusPagamento,
        Double valorPago,
        Long pedidoId
) {

}