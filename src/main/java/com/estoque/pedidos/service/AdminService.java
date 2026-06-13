package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Admin;
import com.estoque.pedidos.dto.request.AdminRequestDTO;
import com.estoque.pedidos.dto.response.AdminResponseDTO;
import com.estoque.pedidos.repository.AdminRepository;
import com.estoque.pedidos.mapper.AdminMapper; // Import adicionado

@Service
public class AdminService {

    private final AdminRepository repository;
    private final AdminMapper mapper; // Declarado como final

    public AdminService(AdminRepository repository, AdminMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AdminResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AdminResponseDTO findById(Long id) {
        Admin admin = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin não encontrado com o ID: " + id));
        return mapper.toResponseDTO(admin);
    }

    public AdminResponseDTO save(AdminRequestDTO requestDTO) {
        Admin admin = mapper.toEntity(requestDTO);
        Admin adminSalvo = repository.save(admin);
        return mapper.toResponseDTO(adminSalvo);
    }

    public AdminResponseDTO update(Long id, AdminRequestDTO requestDTO) {
        Admin adminExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin não encontrado com o ID: " + id));

        mapper.updateEntityFromDTO(requestDTO, adminExistente);

        Admin adminAtualizado = repository.save(adminExistente);
        return mapper.toResponseDTO(adminAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Admin não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}