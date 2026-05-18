package com.estoque.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estoque.pedidos.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}