package com.estoque.pedidos.dto.response;

public class ItemPedidoResponseDTO {

    private Long id;
    private Integer quantidade;
    private Double precoUnitario;
    private Long pedidoId; // Retornamos apenas o ID do pedido para não trazer o histórico inteiro do cliente de forma recursiva
    private ProdutoResponseDTO produto;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public ProdutoResponseDTO getProduto() { return produto; }
    public void setProduto(ProdutoResponseDTO produto) { this.produto = produto; }
    
    
}
