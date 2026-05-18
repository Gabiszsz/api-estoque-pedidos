package com.estoque.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estoque.pedidos.model.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}