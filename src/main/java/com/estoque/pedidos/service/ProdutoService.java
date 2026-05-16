package com.estoque.pedidos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    // LISTAR TODOS
    public List<Produto> findAll() {
        return repository.findAll();
    }

    // BUSCAR POR ID
    public Produto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
    }

    // SALVAR
    public Produto save(Produto produto) {
        return repository.save(produto);
    }

    // ATUALIZAR (Mapeando todos os campos da sua classe Produto)
    public Produto update(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = findById(id);
        
        // Copiando os novos dados para o produto que já existe no banco
        produtoExistente.setSku(produtoAtualizado.getSku());
        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setPrecoVenda(produtoAtualizado.getPrecoVenda());
        produtoExistente.setUnidadeMedida(produtoAtualizado.getUnidadeMedida());
        produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());

        // Atualiza no banco
        return repository.save(produtoExistente);
    }

    // DELETAR
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar. Produto não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}