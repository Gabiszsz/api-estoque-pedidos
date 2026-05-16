package com.estoque.pedidos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estoque.pedidos.model.Estoque;
import com.estoque.pedidos.repository.EstoqueRepository;

@Service
public class EstoqueService {

    private final EstoqueRepository repository;

    public EstoqueService(EstoqueRepository repository) {
        this.repository = repository;
    }

    public List<Estoque> findAll() {
        return repository.findAll();
    }

    public Estoque findById(Long id) {
        return repository.findById(id);
    }

    public Estoque save(Estoque estoque) {
        return repository.save(estoque);
    }

    public Estoque update(Long id, Estoque estoque) {
        return repository.update(id, estoque);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}