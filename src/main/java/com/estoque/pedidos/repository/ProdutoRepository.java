package com.estoque.pedidos.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.estoque.pedidos.model.Produto;

@Component // Mudamos para Component porque é uma simulação de banco em memória
public class ProdutoRepository {

    private final List<Produto> listaProdutos = new ArrayList<>();

    public ProdutoRepository() {
        // Seus produtos iniciais de teste
        listaProdutos.add(new Produto(1L, "PROD001", "Notebook Gamer", 4500.0, "UN", 10));
        listaProdutos.add(new Produto(2L, "PROD002", "Mouse Gamer", 150.0, "UN", 30));
    }

    // LISTAR TODOS
    public List<Produto> findAll() {
        return this.listaProdutos;
    }

    // BUSCAR POR ID (Retorna um Optional, que a Service espera)
    public Optional<Produto> findById(Long id) {
        return listaProdutos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    // SALVAR OU ATUALIZAR
    public Produto save(Produto produto) {
        // Se o produto não tiver ID, geramos um novo
        if (produto.getId() == null) {
            Long proximoId = (long) (listaProdutos.size() + 1);
            produto.setId(proximoId);
            listaProdutos.add(produto);
        } else {
            // Se já tiver ID (caso do update), removemos o antigo e inserimos o atualizado
            deleteById(produto.getId());
            listaProdutos.add(produto);
        }
        return produto;
    }

    // VERIFICAR SE EXISTE POR ID
    public boolean existsById(Long id) {
        return listaProdutos.stream().anyMatch(p -> p.getId().equals(id));
    }

    // DELETAR POR ID
    public void deleteById(Long id) {
        listaProdutos.removeIf(p -> p.getId().equals(id));
    }
}