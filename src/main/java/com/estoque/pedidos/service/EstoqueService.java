package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Estoque;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.EstoqueRequestDTO;
import com.estoque.pedidos.dto.response.EstoqueResponseDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.repository.EstoqueRepository;
import com.estoque.pedidos.repository.ProdutoRepository;

@Service
public class EstoqueService {

    private final EstoqueRepository repository;
    private final ProdutoRepository produtoRepository; // Injetado para buscar o Produto pelo ID

    // Atualizado o construtor para receber ambos os repositórios
    public EstoqueService(EstoqueRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    // LISTAR TODOS (Retorna uma lista de ResponseDTO)
    public List<EstoqueResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID (Retorna um ResponseDTO)
    public EstoqueResponseDTO findById(Long id) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado com o ID: " + id));
        return converteParaResponseDTO(estoque);
    }

    // SALVAR (Recebe RequestDTO e retorna ResponseDTO)
    public EstoqueResponseDTO save(EstoqueRequestDTO requestDTO) {
        // Busca a entidade Produto obrigatória usando o produtoId do DTO
        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + requestDTO.produtoId()));

        Estoque estoque = new Estoque();
        estoque.setQuantidadeAtual(requestDTO.quantidadeAtual());
        estoque.setQuantidadeMinima(requestDTO.quantidadeMinima());
        estoque.setLocalizacao(requestDTO.localizacao());
        estoque.setProduto(produto); // Associa o produto encontrado à entidade Estoque

        Estoque estoqueSalvo = repository.save(estoque);
        return converteParaResponseDTO(estoqueSalvo);
    }

    // ATUALIZAR (Recebe RequestDTO e retorna ResponseDTO)
    public EstoqueResponseDTO update(Long id, EstoqueRequestDTO requestDTO) {
        Estoque estoqueExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado com o ID: " + id));

        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + requestDTO.produtoId()));

        estoqueExistente.setQuantidadeAtual(requestDTO.quantidadeAtual());
        estoqueExistente.setQuantidadeMinima(requestDTO.quantidadeMinima());
        estoqueExistente.setLocalizacao(requestDTO.localizacao());
        estoqueExistente.setProduto(produto);

        Estoque estoqueAtualizado = repository.save(estoqueExistente);
        return converteParaResponseDTO(estoqueAtualizado);
    }

    // DELETAR (Permanece igual, pois usa apenas o ID de rota)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Estoque não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    // MÉTODOS AUXILIARES DE CONVERSÃO
    private EstoqueResponseDTO converteParaResponseDTO(Estoque estoque) {
        ProdutoResponseDTO prodResponse = null;

        // Se houver um produto associado, converte ele para ProdutoResponseDTO
        if (estoque.getProduto() != null) {
            prodResponse = new ProdutoResponseDTO(
                    estoque.getProduto().getId(),
                    estoque.getProduto().getNome(),
                    estoque.getProduto().getPrecoVenda(),
                    estoque.getProduto().getQuantidadeEstoque()
            );
        }

        return new EstoqueResponseDTO(
                estoque.getId(),
                estoque.getQuantidadeAtual(),
                estoque.getQuantidadeMinima(),
                estoque.getLocalizacao(),
                prodResponse
        );
    }
}