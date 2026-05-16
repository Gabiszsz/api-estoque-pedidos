package com.estoque.pedidos.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPedido;
    private LocalDate dataPedido;
    private String status;
    private Double valorTotal;
    private Cliente cliente;

    public Pedido() {
    }

    public Pedido(Long idPedido, LocalDate dataPedido, String status, Double valorTotal, Cliente cliente) {
        this.idPedido = idPedido;
        this.dataPedido = dataPedido;
        this.status = status;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}