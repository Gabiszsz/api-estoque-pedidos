package com.estoque.pedidos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Pagamento;
import com.estoque.pedidos.repository.PagamentoRepository;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public List<Pagamento> findAll() {
        return repository.findAll();
    }

    public Pagamento findById(Long id) {
        // .orElseThrow() abre a "caixa" do Optional ou lança um erro se o ID não existir
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado com o ID: " + id));
    }

    public Pagamento save(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    public Pagamento update(Long id, Pagamento pagamentoAtualizado) {
        Pagamento pagamentoExistente = findById(id);

        // Copia os dados novos para o registro existente
        pagamentoExistente.setMetodoPagamento(pagamentoAtualizado.getMetodoPagamento());
        pagamentoExistente.setDataConfirmacao(pagamentoAtualizado.getDataConfirmacao());
        pagamentoExistente.setStatusPagamento(pagamentoAtualizado.getStatusPagamento());
        pagamentoExistente.setValorPago(pagamentoAtualizado.getValorPago());
        pagamentoExistente.setPedido(pagamentoAtualizado.getPedido());

        // O .save() do JPA faz o UPDATE automaticamente se o ID já existir no objeto
        return repository.save(pagamentoExistente);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar. Pagamento não encontrado com o ID: " + id);
        }
        repository.deleteById(id); // Alterado para o método padrão do JPA
    }
}