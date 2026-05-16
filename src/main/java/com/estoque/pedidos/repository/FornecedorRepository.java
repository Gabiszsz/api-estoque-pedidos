package com.estoque.pedidos.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component; // Importação alterada para Component

import com.estoque.pedidos.model.Fornecedor;

@Component // Alterado de @Repository para @Component para remover o aviso amarelo
public class FornecedorRepository {

    // Adicionado o 'final' para remover o aviso de boa prática do VS Code
    private final List<Fornecedor> listaFornecedores = new ArrayList<>();

    public FornecedorRepository() {

        listaFornecedores.add(
            new Fornecedor(
                1L,
                "12.345.678/0001-99",
                "Tech Distribuidora",
                "Carlos Silva"
            )
        );

        listaFornecedores.add(
            new Fornecedor(
                2L,
                "98.765.432/0001-11",
                "Info Supply",
                "Maria Souza"
            )
        );
    }

    public List<Fornecedor> findAll() {
        return listaFornecedores;
    }

    public Fornecedor findById(Long id) {

        for (Fornecedor fornecedor : listaFornecedores) {
            if (fornecedor.getId().equals(id)) {
                return fornecedor;
            }
        }

        return null;
    }

    public Fornecedor save(Fornecedor fornecedor) {

        fornecedor.setId((long) (listaFornecedores.size() + 1));

        listaFornecedores.add(fornecedor);

        return fornecedor;
    }

    public Fornecedor update(
        Long id,
        Fornecedor fornecedorAtualizado
    ) {

        Fornecedor fornecedorExistente = findById(id);

        if (fornecedorExistente != null) {

            fornecedorExistente.setCnpj(
                fornecedorAtualizado.getCnpj()
            );

            fornecedorExistente.setRazaoSocial(
                fornecedorAtualizado.getRazaoSocial()
            );

            fornecedorExistente.setContatoVendedor(
                fornecedorAtualizado.getContatoVendedor()
            );

            return fornecedorExistente;
        }

        return null;
    }

    public void delete(Long id) {

        listaFornecedores.removeIf(
            fornecedor -> fornecedor.getId().equals(id)
        );
    }
}