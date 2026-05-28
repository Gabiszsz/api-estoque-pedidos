package com.estoque.pedidos.dto.response;

public record ItemPedidoResponseDTO(
        Long id,
        Integer quantidade,
        Double precoUnitario,
        Long pedidoId, // pode ser PedidoResponseDTO pra aninhar
        Long produtoId // ou ProdutoResponseDTO
) {

}