package com.estoque.pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProdutoRequestDTO(
        @NotBlank(message = "O SKU é obrigatório.")
        @Size(max = 20, message = "O SKU deve ter no máximo {max} caracteres.")
        String sku,

        @NotBlank(message = "O nome do produto é obrigatório.")
        String nome,

        @NotNull(message = "O preço de venda é obrigatório.")
        @PositiveOrZero(message = "O preço de venda não pode ser negativo.")
        Double precoVenda,

        @NotBlank(message = "A unidade de medida é obrigatória.")
        String unidadeMedida,

        @NotNull(message = "A quantidade em estoque é obrigatória.")
        @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa.")
        Integer quantidadeEstoque
) {
}