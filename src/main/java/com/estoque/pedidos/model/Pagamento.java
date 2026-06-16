package com.estoque.pedidos.model;

import com.estoque.pedidos.model.enums.MetodoPagamento;
import com.estoque.pedidos.model.enums.StatusPagamento;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPagamento;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorPago;

    private LocalDate dataConfirmacao;

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public Pagamento() {
    }

    public Pagamento(Long idPagamento, BigDecimal valorPago, LocalDate dataConfirmacao,
                     MetodoPagamento metodoPagamento, StatusPagamento statusPagamento, Pedido pedido) {
        this.idPagamento = idPagamento;
        this.valorPago = valorPago;
        this.dataConfirmacao = dataConfirmacao;
        this.metodoPagamento = metodoPagamento;
        this.statusPagamento = statusPagamento;
        this.pedido = pedido;
    }

    public Long getIdPagamento() { return idPagamento; }
    public void setIdPagamento(Long idPagamento) { this.idPagamento = idPagamento; }
    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal valorPago) { this.valorPago = valorPago; }
    public LocalDate getDataConfirmacao() { return dataConfirmacao; }
    public void setDataConfirmacao(LocalDate dataConfirmacao) { this.dataConfirmacao = dataConfirmacao; }
    public MetodoPagamento getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(MetodoPagamento metodoPagamento) { this.metodoPagamento = metodoPagamento; }
    public StatusPagamento getStatusPagamento() { return statusPagamento; }
    public void setStatusPagamento(StatusPagamento statusPagamento) { this.statusPagamento = statusPagamento; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}