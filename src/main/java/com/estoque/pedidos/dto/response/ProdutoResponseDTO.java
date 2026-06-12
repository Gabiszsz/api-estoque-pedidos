package com.estoque.pedidos.dto.response;

import org.springframework.hateoas.RepresentationModel;

public class ProdutoResponseDTO extends RepresentationModel<ProdutoResponseDTO> {
    
    private Long id;
    private String nome;
    private Double precoVenda;
    private Integer quantidadeEstoque;

    public ProdutoResponseDTO() {
    }

    public ProdutoResponseDTO(Long id, String nome, Double precoVenda, Integer quantidadeEstoque) {
        this.id = id;
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(Double precoVenda) { this.precoVenda = precoVenda; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
}