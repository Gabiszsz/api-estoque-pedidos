package com.estoque.pedidos.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
public record Preco(
        @Column(name = "preco_valor", nullable = false, precision = 10, scale = 2)
        BigDecimal valor,

        @Column(name = "preco_moeda", length = 3)
        String moeda
) {
    public Preco {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor do preço não pode ser negativo");
        }
        if (moeda == null || moeda.isBlank()) {
            moeda = "BRL";
        }
        // Força sempre duas casas decimais no momento da criação
        valor = valor.setScale(2, RoundingMode.HALF_UP);
    }
}