package com.estoque.pedidos.dto.request;

public record EstoqueRequestDTO(
        Integer quantidadeAtual,
        Integer quantidadeMinima,
        String localizacao,
        Long produtoId
) {

}