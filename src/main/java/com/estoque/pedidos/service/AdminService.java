package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Admin;
import com.estoque.pedidos.dto.request.AdminRequestDTO;
import com.estoque.pedidos.dto.response.AdminResponseDTO;
import com.estoque.pedidos.repository.AdminRepository;

@Service
public class AdminService {

    private final AdminRepository repository;

    public AdminService(AdminRepository repository) {
        this.repository = repository;
    }

    public List<AdminResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    public AdminResponseDTO findById(Long id) {
        Admin admin = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin não encontrado com o ID: " + id));
        return converteParaResponseDTO(admin);
    }

    public AdminResponseDTO save(AdminRequestDTO requestDTO) {
        Admin admin = new Admin();
        admin.setLogin(requestDTO.login());
        admin.setSenha(requestDTO.senha());
        admin.setNivelAcesso(requestDTO.nivelAcesso());

        Admin adminSalvo = repository.save(admin);
        return converteParaResponseDTO(adminSalvo);
    }

    public AdminResponseDTO update(Long id, AdminRequestDTO requestDTO) {
        Admin adminExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin não encontrado com o ID: " + id));

        adminExistente.setLogin(requestDTO.login());
        adminExistente.setSenha(requestDTO.senha());
        adminExistente.setNivelAcesso(requestDTO.nivelAcesso());

        Admin adminAtualizado = repository.save(adminExistente);
        return converteParaResponseDTO(adminAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Admin não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private AdminResponseDTO converteParaResponseDTO(Admin admin) {
        return new AdminResponseDTO(admin.getId(), admin.getLogin(), admin.getNivelAcesso());
    }
}