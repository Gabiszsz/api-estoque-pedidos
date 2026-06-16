package com.estoque.pedidos.model;

import java.io.Serializable;
import jakarta.persistence.*;
import com.estoque.pedidos.model.vo.Preco;

@Entity
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "valor", column = @Column(name = "preco_unitario_valor", nullable = false, precision = 10, scale = 2)),
            @AttributeOverride(name = "moeda", column = @Column(name = "preco_unitario_moeda", length = 3))
    })
    private Preco precoUnitario;

    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Produto produto;

    public ItemPedido() {
    }

    public ItemPedido(Long id, Integer quantidade, Preco precoUnitario, Pedido pedido, Produto produto) {
        this.id = id;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.pedido = pedido;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public Preco getPrecoUnitario() {
        return precoUnitario;
    }
    public void setPrecoUnitario(Preco precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}