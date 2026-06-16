package com.estoque.pedidos.dto.response;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        BigDecimal preco,
        Integer quantidade,
        CategoriaResponseDTO categoria
) {}