package com.estoque.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estoque.pedidos.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}