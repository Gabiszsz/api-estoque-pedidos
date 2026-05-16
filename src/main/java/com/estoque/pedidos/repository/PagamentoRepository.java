package com.estoque.pedidos.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component; // Importação alterada para Component

import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.model.Pagamento;
import com.estoque.pedidos.model.Pedido;

@Component // Alterado de @Repository para @Component para remover o aviso amarelo
public class PagamentoRepository {

    // Adicionado o 'final' para remover o aviso de boa prática do VS Code
    private final List<Pagamento> listaPagamentos = new ArrayList<>();

    public PagamentoRepository() {

        Cliente cliente = new Cliente(
            1L,
            "111.111.111-11",
            "Rua A"
        );

        Pedido pedido = new Pedido(
            1L,
            LocalDate.now(),
            "FINALIZADO",
            4500.0,
            cliente
        );

        listaPagamentos.add(
            new Pagamento(
                1L,
                "PIX",
                LocalDate.now(),
                "PAGO",
                4500.0,
                pedido
            )
        );
    }

    public List<Pagamento> findAll() {
        return listaPagamentos;
    }

    public Pagamento findById(Long id) {

        for (Pagamento pagamento : listaPagamentos) {
            if (pagamento.getIdPagamento().equals(id)) {
                return pagamento;
            }
        }

        return null;
    }

    public Pagamento save(Pagamento pagamento) {

        pagamento.setIdPagamento((long) (listaPagamentos.size() + 1));

        listaPagamentos.add(pagamento);

        return pagamento;
    }

    public Pagamento update(Long id, Pagamento pagamentoAtualizado) {

        Pagamento pagamentoExistente = findById(id);

        if (pagamentoExistente != null) {

            pagamentoExistente.setMetodoPagamento(pagamentoAtualizado.getMetodoPagamento());
            pagamentoExistente.setDataConfirmacao(pagamentoAtualizado.getDataConfirmacao());
            pagamentoExistente.setStatusPagamento(pagamentoAtualizado.getStatusPagamento());
            pagamentoExistente.setValorPago(pagamentoAtualizado.getValorPago());
            pagamentoExistente.setPedido(pagamentoAtualizado.getPedido());

            return pagamentoExistente;
        }

        return null;
    }

    public void delete(Long id) {

        listaPagamentos.removeIf(
            pagamento -> pagamento.getIdPagamento().equals(id)
        );
    }
}