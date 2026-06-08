package com.estoque.pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record PagamentoRequestDTO(
        @NotBlank(message = "O método de pagamento é obrigatório.")
        String metodoPagamento,

        @NotNull(message = "A data de confirmação é obrigatória.")
        LocalDate dataConfirmacao,

        @NotBlank(message = "O status do pagamento é obrigatório.")
        String statusPagamento,

        @NotNull(message = "O valor pago é obrigatório.")
        @Positive(message = "O valor pago deve ser maior do que zero.")
        Double valorPago,

        @NotNull(message = "O ID do pedido é obrigatório.")
        Long pedidoId
) {
}