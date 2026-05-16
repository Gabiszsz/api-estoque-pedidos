package com.estoque.pedidos.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component; // Importação alterada para Component

import com.estoque.pedidos.model.Admin;

@Component // Alterado de @Repository para @Component para remover o aviso amarelo
public class AdminRepository {

    // Adicionado o 'final' para remover o aviso de boa prática do VS Code
    private final List<Admin> listaAdmins = new ArrayList<>();

    public AdminRepository() {

        listaAdmins.add(
            new Admin(
                1L,
                "admin",
                "123456",
                "TOTAL"
            )
        );

        listaAdmins.add(
            new Admin(
                2L,
                "gabriella",
                "123456",
                "GERENTE"
            )
        );
    }

    public List<Admin> findAll() {
        return listaAdmins;
    }

    public Admin findById(Long id) {

        for (Admin admin : listaAdmins) {
            if (admin.getId().equals(id)) {
                return admin;
            }
        }

        return null;
    }

    public Admin save(Admin admin) {

        admin.setId(
            (long) (listaAdmins.size() + 1)
        );

        listaAdmins.add(admin);

        return admin;
    }

    public Admin update(
        Long id,
        Admin adminAtualizado
    ) {

        Admin adminExistente = findById(id);

        if (adminExistente != null) {

            adminExistente.setLogin(
                adminAtualizado.getLogin()
            );

            adminExistente.setSenha(
                adminAtualizado.getSenha()
            );

            adminExistente.setNivelAcesso(
                adminAtualizado.getNivelAcesso()
            );

            return adminExistente;
        }

        return null;
    }

    public void delete(Long id) {

        listaAdmins.removeIf(
            admin -> admin.getId().equals(id)
        );
    }
}
