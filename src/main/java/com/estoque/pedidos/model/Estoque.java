package com.estoque.pedidos.model;

import java.io.Serializable;

public class Estoque implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer quantidadeAtual;
    private Integer quantidadeMinima;
    private String localizacao;
    private Produto produto;

    public Estoque() {
    }

    public Estoque(Long id, Integer quantidadeAtual, Integer quantidadeMinima, String localizacao, Produto produto) {
        this.id = id;
        this.quantidadeAtual = quantidadeAtual;
        this.quantidadeMinima = quantidadeMinima;
        this.localizacao = localizacao;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public Integer getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantidadeAtual(Integer quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public void setQuantidadeMinima(Integer quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public boolean estoqueBaixo() {
        return quantidadeAtual != null && quantidadeMinima != null && quantidadeAtual <= quantidadeMinima;
    }
}
