package com.estoque.pedidos.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Estoque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidadeAtual;
    private Integer quantidadeMinima;
    private String localizacao;
    private String notaFiscal;
    private LocalDate dataEntrada;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;


    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    public Estoque() {
    }

    public Estoque(Long id, Integer quantidadeAtual, Integer quantidadeMinima, String localizacao, Produto produto) {
        this.id = id;
        this.quantidadeAtual = quantidadeAtual;
        this.quantidadeMinima = quantidadeMinima;
        this.localizacao = localizacao;
        this.produto = produto;
        this.notaFiscal = notaFiscal;
        this.dataEntrada = dataEntrada;
        this.fornecedor = fornecedor;
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

    public String getNotaFiscal() {return notaFiscal;}

    public void setNotaFiscal(String notaFiscal) {this.notaFiscal = notaFiscal;}

    public LocalDate getDataEntrada() {return dataEntrada;}

    public void setDataEntrada(LocalDate dataEntrada) {this.dataEntrada = dataEntrada;}

    public Fornecedor getFornecedor() {return fornecedor;}

    public void setFornecedor(Fornecedor fornecedor) {this.fornecedor = fornecedor;}

    public boolean estoqueBaixo() {
        return quantidadeAtual != null && quantidadeMinima != null && quantidadeAtual <= quantidadeMinima;
    }
}
