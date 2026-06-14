package com.estoque.pedidos.model.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public record Preco(
        @Column(name = "preco_valor", nullable = false)
        Double valor,

        @Column(name = "preco_moeda", length = 3)
        String moeda
) {
    public Preco {
        if (valor == null || valor < 0) {
            throw new IllegalArgumentException("O valor do preço não pode ser negativo");
        }
        if (moeda == null || moeda.isBlank()) {
            moeda = "BRL";
        }
    }
}