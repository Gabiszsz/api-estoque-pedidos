package com.estoque.pedidos.dto.request;

import java.time.LocalDate;

public class PedidoRequestDTO {

    private LocalDate dataPedido;
    private String status;
    private Double valorTotal;
    private Long clienteId;

    // Getters e Setters
    public LocalDate getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDate dataPedido) { this.dataPedido = dataPedido; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId;
    }
    
}
