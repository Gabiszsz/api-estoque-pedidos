package com.estoque.pedidos.dto.response;

public class EstoqueResponseDTO {

   private Long id;
    private Integer quantidadeAtual;
    private Integer quantidadeMinima;
    private String localizacao;
    private boolean estoqueBaixo; // Calculado pela regra de negócio do modelo
    private ProdutoResponseDTO produto;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getQuantidadeAtual() { return quantidadeAtual; }
    public void setQuantidadeAtual(Integer quantidadeAtual) { this.quantidadeAtual = quantidadeAtual; }
    public Integer getQuantidadeMinima() { return quantidadeMinima; }
    public void setQuantidadeMinima(Integer quantidadeMinima) { this.quantidadeMinima = quantidadeMinima; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public boolean isEstoqueBaixo() { return estoqueBaixo; }
    public void setEstoqueBaixo(boolean estoqueBaixo) { this.estoqueBaixo = estoqueBaixo; }
    public ProdutoResponseDTO getProduto() { return produto; }
    public void setProduto(ProdutoResponseDTO produto) { this.produto = produto; 
}
    
}
