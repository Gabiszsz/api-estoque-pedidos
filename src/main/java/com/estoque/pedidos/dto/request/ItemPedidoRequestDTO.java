package com.estoque.pedidos.dto.request;

public class ItemPedidoRequestDTO {

    private Integer quantidade;
    private Double precoUnitario;
    private Long pedidoId;
    private Long produtoId;

    // Getters e Setters
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId;
    }
    
}
