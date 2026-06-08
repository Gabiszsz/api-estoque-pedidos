package com.estoque.pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminRequestDTO(
        @NotBlank(message = "O login é obrigatório.")
        @Size(min = 3, max = 50, message = "O login deve ter entre {min} e {max} caracteres.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, message = "A senha deve ter no mínimo {min} caracteres.")
        String senha,

        @NotBlank(message = "O nível de acesso é obrigatório.")
        String nivelAcesso
) {
}