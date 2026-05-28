package com.estoque.pedidos.dto.response;

public record EstoqueResponseDTO(
        Long id,
        Integer quantidadeAtual,
        Integer quantidadeMinima,
        String localizacao,
        ProdutoResponseDTO produto // Retorna os detalhes do produto associado
) {

}