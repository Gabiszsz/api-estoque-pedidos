package com.estoque.pedidos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Fornecedor;
import com.estoque.pedidos.repository.FornecedorRepository;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    public List<Fornecedor> findAll() {
        return repository.findAll();
    }

    public Fornecedor findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com o ID: " + id));
    }

    public Fornecedor save(Fornecedor fornecedor) {
        return repository.save(fornecedor);
    }

    public Fornecedor update(Long id, Fornecedor fornecedorAtualizado) {
        Fornecedor fornecedorExistente = findById(id);

        fornecedorExistente.setCnpj(fornecedorAtualizado.getCnpj());
        fornecedorExistente.setRazaoSocial(fornecedorAtualizado.getRazaoSocial());
        fornecedorExistente.setContatoVendedor(fornecedorAtualizado.getContatoVendedor());

        return repository.save(fornecedorExistente);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Fornecedor não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}