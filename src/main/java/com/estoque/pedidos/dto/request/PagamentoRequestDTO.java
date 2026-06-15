package com.estoque.pedidos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import com.estoque.pedidos.model.enums.MetodoPagamento;

public record PagamentoRequestDTO(
        @NotNull(message = "O ID do pedido é obrigatório.")
        Long pedidoId,

        @NotNull(message = "O valor pago é obrigatório.")
        @Positive(message = "O valor deve ser maior que zero.")
        Double valorPago,

        @NotNull(message = "O método de pagamento é obrigatório.")
        MetodoPagamento metodoPagamento,

        @NotNull(message = "A data de confirmação é obrigatória.")
        LocalDate dataConfirmacao
) {
}