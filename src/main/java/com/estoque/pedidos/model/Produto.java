package com.estoque.pedidos.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String nome;
    private Double precoVenda;
    private String unidadeMedida;
    private Integer quantidadeEstoque;

    public Produto() {
    }

    public Produto(Long id, String sku, String nome, Double precoVenda, String unidadeMedida, Integer quantidadeEstoque) {
        this.id = id;
        this.sku = sku;
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.unidadeMedida = unidadeMedida;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((sku == null) ? 0 : sku.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((precoVenda == null) ? 0 : precoVenda.hashCode());
        result = prime * result + ((unidadeMedida == null) ? 0 : unidadeMedida.hashCode());
        result = prime * result + ((quantidadeEstoque == null) ? 0 : quantidadeEstoque.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Produto other = (Produto) obj;

        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        if (sku == null) {
            if (other.sku != null)
                return false;
        } else if (!sku.equals(other.sku))
            return false;

        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;

        if (precoVenda == null) {
            if (other.precoVenda != null)
                return false;
        } else if (!precoVenda.equals(other.precoVenda))
            return false;

        if (unidadeMedida == null) {
            if (other.unidadeMedida != null)
                return false;
        } else if (!unidadeMedida.equals(other.unidadeMedida))
            return false;

        if (quantidadeEstoque == null) {
            if (other.quantidadeEstoque != null)
                return false;
        } else if (!quantidadeEstoque.equals(other.quantidadeEstoque))
            return false;

        return true;
    }

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


    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
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

    public void baixarEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade para baixar deve ser maior que zero.");
        }

        if (this.quantidadeEstoque == null || this.quantidadeEstoque < quantidade) {
            throw new RuntimeException(
                "Estoque insuficiente para o produto: " + this.nome +
                ". Disponível: " + this.quantidadeEstoque +
                ", Solicitado: " + quantidade
            );
        }

        this.quantidadeEstoque -= quantidade;
    }

    public void adicionarEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade para adicionar deve ser maior que zero.");
        }

        if (this.quantidadeEstoque == null) {
            this.quantidadeEstoque = 0;
        }

        this.quantidadeEstoque += quantidade;
    }
}