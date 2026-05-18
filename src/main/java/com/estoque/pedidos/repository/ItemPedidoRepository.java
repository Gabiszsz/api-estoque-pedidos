package com.estoque.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estoque.pedidos.model.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}