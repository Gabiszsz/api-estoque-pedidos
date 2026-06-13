package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Categoria;
import com.estoque.pedidos.dto.request.CategoriaRequestDTO;
import com.estoque.pedidos.dto.response.CategoriaResponseDTO;
import com.estoque.pedidos.repository.CategoriaRepository;
import com.estoque.pedidos.mapper.CategoriaMapper; // Import adicionado

@Service
public class CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper; // Declarado como final

    public CategoriaService(CategoriaRepository repository, CategoriaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CategoriaResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));
        return mapper.toResponseDTO(categoria);
    }

    public CategoriaResponseDTO save(CategoriaRequestDTO requestDTO) {
        Categoria categoria = mapper.toEntity(requestDTO);
        Categoria categoriaSalvo = repository.save(categoria);
        return mapper.toResponseDTO(categoriaSalvo);
    }

    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO requestDTO) {
        Categoria categoriaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));

        mapper.updateEntityFromDTO(requestDTO, categoriaExistente);

        Categoria categoriaAtualizada = repository.save(categoriaExistente);
        return mapper.toResponseDTO(categoriaAtualizada);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }
}