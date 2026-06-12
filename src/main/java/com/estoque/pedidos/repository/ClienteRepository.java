package com.estoque.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estoque.pedidos.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
     // Verifica se já existe o CPF no cadastro geral (usado no save)
    boolean existsByCpf(String cpf);

    // Verifica se o CPF existe em OUTRO cliente que não seja o atual (usado no update)
    boolean existsByCpfAndIdNot(String cpf, Long id);
}
