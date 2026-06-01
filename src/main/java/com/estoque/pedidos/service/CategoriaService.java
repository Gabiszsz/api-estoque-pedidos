package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Categoria;
import com.estoque.pedidos.dto.request.CategoriaRequestDTO;
import com.estoque.pedidos.dto.response.CategoriaResponseDTO;
import com.estoque.pedidos.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));
        return converteParaResponseDTO(categoria);
    }

    public CategoriaResponseDTO save(CategoriaRequestDTO requestDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(requestDTO.nome());

        Categoria categoriaSalvo = repository.save(categoria);
        return converteParaResponseDTO(categoriaSalvo);
    }

    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO requestDTO) {
        Categoria categoriaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));

        categoriaExistente.setNome(requestDTO.nome());

        Categoria categoriaAtualizada = repository.save(categoriaExistente);
        return converteParaResponseDTO(categoriaAtualizada);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private CategoriaResponseDTO converteParaResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome());
    }
}