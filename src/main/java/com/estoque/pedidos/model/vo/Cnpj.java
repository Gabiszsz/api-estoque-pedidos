package com.estoque.pedidos.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Cnpj(
        @Column(name = "cnpj", nullable = false, unique = true, length = 14)
        String valor
) {
    public Cnpj {
        if (valor == null || !valor.matches("\\d{14}")) {
            throw new IllegalArgumentException("CNPJ inválido. Deve conter exatamente 14 dígitos numéricos.");
        }
    }
}