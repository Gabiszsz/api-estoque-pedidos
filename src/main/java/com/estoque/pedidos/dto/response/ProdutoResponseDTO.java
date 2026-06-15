package com.estoque.pedidos.dto.response;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        Double preco,
        Integer quantidade,
        CategoriaResponseDTO categoria
) {
}