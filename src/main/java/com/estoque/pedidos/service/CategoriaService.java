package com.estoque.pedidos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Categoria;
import com.estoque.pedidos.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public Categoria findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));
    }

    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    public Categoria update(Long id, Categoria categoriaAtualizada) {
        Categoria categoriaExistente = findById(id);

        categoriaExistente.setNome(categoriaAtualizada.getNome());

        return repository.save(categoriaExistente);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }
}