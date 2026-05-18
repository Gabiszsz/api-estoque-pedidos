package com.estoque.pedidos.model;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;


@Entity
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPagamento;
    private String metodoPagamento;
    private LocalDate dataConfirmacao;
    private String statusPagamento;
    private Double valorPago;

    @ManyToOne
    private Pedido pedido;

    public Pagamento() {
    }

    public Pagamento(
        Long idPagamento,
        String metodoPagamento,
        LocalDate dataConfirmacao,
        String statusPagamento,
        Double valorPago,
        Pedido pedido
    ) {
        this.idPagamento = idPagamento;
        this.metodoPagamento = metodoPagamento;
        this.dataConfirmacao = dataConfirmacao;
        this.statusPagamento = statusPagamento;
        this.valorPago = valorPago;
        this.pedido = pedido;
    }

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public LocalDate getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDate dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
