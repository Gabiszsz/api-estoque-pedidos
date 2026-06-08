package com.estoque.pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record FornecedorRequestDTO(
        @NotBlank(message = "O CNPJ é obrigatório.")
        @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos numéricos.")
        String cnpj,

        @NotBlank(message = "A razão social é obrigatória.")
        String razaoSocial,

        @NotBlank(message = "O contacto do vendedor é obrigatório.")
        String contatoVendedor
) {
}