package com.estoque.pedidos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estoque.pedidos.model.Fornecedor;
import com.estoque.pedidos.repository.FornecedorRepository;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(
        FornecedorRepository repository
    ) {
        this.repository = repository;
    }

    public List<Fornecedor> findAll() {
        return repository.findAll();
    }

    public Fornecedor findById(Long id) {
        return repository.findById(id);
    }

    public Fornecedor save(Fornecedor fornecedor) {
        return repository.save(fornecedor);
    }

    public Fornecedor update(
        Long id,
        Fornecedor fornecedor
    ) {
        return repository.update(id, fornecedor);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}