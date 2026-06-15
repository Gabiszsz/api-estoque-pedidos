package com.estoque.pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record EstoqueRequestDTO(
        @NotNull(message = "A quantidade atual é obrigatória.")
        @PositiveOrZero(message = "A quantidade atual não pode ser negativa.")
        Integer quantidadeAtual,

        @NotNull(message = "A quantidade mínima é obrigatória.")
        @PositiveOrZero(message = "A quantidade mínima não pode ser negativa.")
        Integer quantidadeMinima,

        @NotBlank(message = "A localização do stock é obrigatória.")
        String localizacao,

        @NotNull(message = "O ID do produto associado é obrigatório.")
        Long produtoId,

        @NotNull(message = "O ID do fornecedor é obrigatório.")
        Long fornecedorId,

        @NotBlank(message = "A Nota Fiscal é obrigatória.")
        String notaFiscal
) {
}