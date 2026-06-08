package com.estoque.pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(
        @NotBlank(message = "O nome da categoria é obrigatório.")
        @Size(min = 2, max = 100, message = "O nome da categoria deve ter entre {min} e {max} caracteres.")
        String nome
) {
}