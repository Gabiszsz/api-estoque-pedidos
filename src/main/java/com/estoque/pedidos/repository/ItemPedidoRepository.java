package com.estoque.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estoque.pedidos.model.ItemPedido;
import java.util.Optional;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    //  SELECT * FROM item_pedido WHERE pedido_id = ? AND produto_id = ?
    Optional<ItemPedido> findByPedido_IdPedidoAndProduto_Id(Long idPedido, Long idProduto);
}