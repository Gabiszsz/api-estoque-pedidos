package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    // LISTAR TODOS (Retorna uma lista de ResponseDTO)
    public List<ProdutoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID (Retorna um ResponseDTO)
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
        return converteParaResponseDTO(produto);
    }

    // SALVAR (Recebe RequestDTO e retorna ResponseDTO)
    public ProdutoResponseDTO save(ProdutoRequestDTO requestDTO) {
        Produto produto = converteParaEntidade(requestDTO);
        Produto produtoSalvo = repository.save(produto);
        return converteParaResponseDTO(produtoSalvo);
    }

    // ATUALIZAR (Recebe RequestDTO e retorna ResponseDTO)
    public ProdutoResponseDTO update(Long id, ProdutoRequestDTO requestDTO) {
        // Busca a entidade direto do banco para atualizar
        Produto produtoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));

        produtoExistente.setSku(requestDTO.sku());
        produtoExistente.setNome(requestDTO.nome());
        produtoExistente.setPrecoVenda(requestDTO.precoVenda());
        produtoExistente.setUnidadeMedida(requestDTO.unidadeMedida());
        produtoExistente.setQuantidadeEstoque(requestDTO.quantidadeEstoque());

        Produto produtoAtualizado = repository.save(produtoExistente);
        return converteParaResponseDTO(produtoAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar. Produto não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    // MÉTODOS AUXILIARES DE CONVERSÃO (Mapeamento manual)
    private ProdutoResponseDTO converteParaResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPrecoVenda(), // preço de venda mapeado para o campo 'preco' do DTO
                produto.getQuantidadeEstoque()
        );
    }

    private Produto converteParaEntidade(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setSku(dto.sku());
        produto.setNome(dto.nome());
        produto.setPrecoVenda(dto.precoVenda());
        produto.setUnidadeMedida(dto.unidadeMedida());
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());
        return produto;
    }
}