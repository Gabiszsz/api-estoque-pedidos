package com.estoque.pedidos.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component; // Importação alterada para Component

import com.estoque.pedidos.model.Estoque;
import com.estoque.pedidos.model.Produto;

@Component // Alterado de @Repository para @Component para remover o aviso amarelo
public class EstoqueRepository {

    // Adicionado o 'final' para remover o aviso de boa prática do VS Code
    private final List<Estoque> listaEstoques = new ArrayList<>();

    public EstoqueRepository() {
        Produto produto = new Produto(1L, "PROD001", "Notebook Gamer", 4500.0, "UN", 10);

        listaEstoques.add(new Estoque(1L, 10, 3, "Prateleira A1", produto));
    }

    public List<Estoque> findAll() {
        return listaEstoques;
    }

    public Estoque findById(Long id) {
        for (Estoque estoque : listaEstoques) {
            if (estoque.getId().equals(id)) {
                return estoque;
            }
        }
        return null;
    }

    public Estoque save(Estoque estoque) {
        estoque.setId((long) (listaEstoques.size() + 1));
        listaEstoques.add(estoque);
        return estoque;
    }

    public Estoque update(Long id, Estoque estoqueAtualizado) {
        Estoque estoqueExistente = findById(id);

        if (estoqueExistente != null) {
            estoqueExistente.setQuantidadeAtual(estoqueAtualizado.getQuantidadeAtual());
            estoqueExistente.setQuantidadeMinima(estoqueAtualizado.getQuantidadeMinima());
            estoqueExistente.setLocalizacao(estoqueAtualizado.getLocalizacao());
            estoqueExistente.setProduto(estoqueAtualizado.getProduto());

            return estoqueExistente;
        }

        return null;
    }

    public void delete(Long id) {
        listaEstoques.removeIf(estoque -> estoque.getId().equals(id));
    }
}