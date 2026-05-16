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
        return repository.findById(id);
    }

    public Pagamento save(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    public Pagamento update(Long id, Pagamento pagamento) {
        return repository.update(id, pagamento);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}