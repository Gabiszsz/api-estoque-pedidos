package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.repository.ProdutoRepository;
import com.estoque.pedidos.mapper.ProdutoMapper; // Import do novo Mapper

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper; // Declarado como final

    // Injeção limpa por construtor exigida pelo Spring e boas práticas
    public ProdutoService(ProdutoRepository repository, ProdutoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProdutoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO) // Usa o MapStruct
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO findById(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
        return mapper.toResponseDTO(produto);
    }

    public ProdutoResponseDTO save(ProdutoRequestDTO requestDTO) {
        Produto produto = mapper.toEntity(requestDTO);
        Produto produtoSalvo = repository.save(produto);
        return mapper.toResponseDTO(produtoSalvo);
    }

    public ProdutoResponseDTO update(Long id, ProdutoRequestDTO requestDTO) {
        Produto produtoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));

        // Atualiza os campos do produto existente automaticamente através do MapStruct
        mapper.updateEntityFromDTO(requestDTO, produtoExistente);

        Produto produtoAtualizado = repository.save(produtoExistente);
        return mapper.toResponseDTO(produtoAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar. Produto não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}