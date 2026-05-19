package com.estoque.pedidos.dto.request;

public class EstoqueRequestDTO {
 
    private Integer quantidadeAtual;
    private Integer quantidadeMinima;
    private String localizacao;
    private Long produtoId; // Apenas o ID para vincular

    // Getters e Setters
    public Integer getQuantidadeAtual() { return quantidadeAtual; }
    public void setQuantidadeAtual(Integer quantidadeAtual) { this.quantidadeAtual = quantidadeAtual; }
    public Integer getQuantidadeMinima() { return quantidadeMinima; }
    public void setQuantidadeMinima(Integer quantidadeMinima) { this.quantidadeMinima = quantidadeMinima; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId;
    }
}
