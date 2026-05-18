package com.estoque.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estoque.pedidos.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}