package com.estoque.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estoque.pedidos.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}