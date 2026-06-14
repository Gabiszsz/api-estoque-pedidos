package com.estoque.pedidos.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Cpf(
        @Column(name = "cpf", nullable = false, unique = true, length = 11)
        String valor
) {
    public Cpf {
        if (valor == null || !valor.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido. Deve conter exatamente 11 dígitos numéricos.");
        }
    }
}