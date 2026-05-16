package com.estoque.pedidos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estoque.pedidos.model.Admin;
import com.estoque.pedidos.repository.AdminRepository;

@Service
public class AdminService {

    private final AdminRepository repository;

    public AdminService(
        AdminRepository repository
    ) {
        this.repository = repository;
    }

    public List<Admin> findAll() {
        return repository.findAll();
    }

    public Admin findById(Long id) {
        return repository.findById(id);
    }

    public Admin save(Admin admin) {
        return repository.save(admin);
    }

    public Admin update(Long id, Admin admin) {
        return repository.update(id, admin);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}