package com.estoque.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estoque.pedidos.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Faz a busca mapeando o atributo interno 'valor' do CpfEmbeddable
    boolean existsByCpfValor(String valor);
}