package com.estoque.pedidos.model;

import java.io.Serializable;
import jakarta.persistence.*;
import com.estoque.pedidos.model.vo.Preco;
import com.estoque.pedidos.exception.EstoqueInsuficienteException;

@Entity
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private String nome;

    @Embedded
    private Preco preco; // Usando o VO igual ao do professor

    private String unidadeMedida;
    private Integer quantidadeEstoque;

    public Produto() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Preco getPreco() {
        return preco;
    }
    public void setPreco(Preco preco) {
        this.preco = preco;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }
    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // Regras de negócio
    public void baixarEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        if (this.quantidadeEstoque == null || this.quantidadeEstoque < quantidade) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + this.nome);
        }
        this.quantidadeEstoque -= quantidade;
    }

    public void adicionarEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        if (this.quantidadeEstoque == null) { this.quantidadeEstoque = 0; }
        this.quantidadeEstoque += quantidade;
    }
}