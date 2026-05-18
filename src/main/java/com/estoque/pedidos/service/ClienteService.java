package com.estoque.pedidos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
    }

    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    public Cliente update(Long id, Cliente clienteAtualizado) {
        Cliente clienteExistente = findById(id);

        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setEnderecoCompleto(clienteAtualizado.getEnderecoCompleto());

        return repository.save(clienteExistente);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}