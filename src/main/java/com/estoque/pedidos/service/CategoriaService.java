package com.estoque.pedidos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estoque.pedidos.model.Categoria;
import com.estoque.pedidos.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(
        CategoriaRepository repository
    ) {
        this.repository = repository;
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public Categoria findById(Long id) {
        return repository.findById(id);
    }

    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    public Categoria update(
        Long id,
        Categoria categoria
    ) {
        return repository.update(id, categoria);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}