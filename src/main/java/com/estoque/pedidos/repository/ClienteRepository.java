package com.estoque.pedidos.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component; // Importação alterada aqui

import com.estoque.pedidos.model.Cliente;

@Component // Mudado de @Repository para @Component para tirar o aviso amarelo
public class ClienteRepository {

   private final List<Cliente> listaClientes = new ArrayList<>();

    public ClienteRepository() {
        listaClientes.add(new Cliente(1L, "111.111.111-11", "Rua A, número 100"));
        listaClientes.add(new Cliente(2L, "222.222.222-22", "Rua B, número 200"));
    }

    public List<Cliente> findAll() {
        return listaClientes;
    }

    public Cliente findById(Long id) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente save(Cliente cliente) {
        cliente.setId((long) (listaClientes.size() + 1));
        listaClientes.add(cliente);
        return cliente;
    }

    public Cliente update(Long id, Cliente clienteAtualizado) {
        Cliente clienteExistente = findById(id);

        if (clienteExistente != null) {
            clienteExistente.setCpf(clienteAtualizado.getCpf());
            clienteExistente.setEnderecoCompleto(clienteAtualizado.getEnderecoCompleto());

            return clienteExistente;
        }

        return null;
    }

    public void delete(Long id) {
        listaClientes.removeIf(cliente -> cliente.getId().equals(id));
    }
}