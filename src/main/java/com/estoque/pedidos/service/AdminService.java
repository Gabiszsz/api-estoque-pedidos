package com.estoque.pedidos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Admin;
import com.estoque.pedidos.repository.AdminRepository;

@Service
public class AdminService {

    private final AdminRepository repository;

    public AdminService(AdminRepository repository) {
        this.repository = repository;
    }

    public List<Admin> findAll() {
        return repository.findAll();
    }

    public Admin findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin não encontrado com o ID: " + id));
    }

    public Admin save(Admin admin) {
        return repository.save(admin);
    }

    public Admin update(Long id, Admin adminAtualizado) {
        Admin adminExistente = findById(id);

        adminExistente.setLogin(adminAtualizado.getLogin());
        adminExistente.setSenha(adminAtualizado.getSenha());
        adminExistente.setNivelAcesso(adminAtualizado.getNivelAcesso());

        return repository.save(adminExistente);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Admin não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}