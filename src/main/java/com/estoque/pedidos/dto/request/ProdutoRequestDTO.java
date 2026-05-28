package com.estoque.pedidos.dto.request;

public record ProdutoRequestDTO(
        String sku,
        String nome,
        Double precoVenda,
        String unidadeMedida,
        Integer quantidadeEstoque
) {

}