package com.estoque.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estoque.pedidos.model.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}