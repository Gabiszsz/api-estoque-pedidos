package com.estoque.pedidos.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component; // Importação alterada para Component

import com.estoque.pedidos.model.Categoria;

@Component // Alterado de @Repository para @Component para remover o aviso amarelo
public class CategoriaRepository {

    // Adicionado o 'final' para remover o aviso de boa prática do VS Code
    private final List<Categoria> listaCategorias = new ArrayList<>();

    public CategoriaRepository() {

        listaCategorias.add(
            new Categoria(
                1L,
                "Informática"
            )
        );

        listaCategorias.add(
            new Categoria(
                2L,
                "Periféricos"
            )
        );
    }

    public List<Categoria> findAll() {
        return listaCategorias;
    }

    public Categoria findById(Long id) {

        for (Categoria categoria : listaCategorias) {
            if (categoria.getId().equals(id)) {
                return categoria;
            }
        }

        return null;
    }

    public Categoria save(Categoria categoria) {

        categoria.setId(
            (long) (listaCategorias.size() + 1)
        );

        listaCategorias.add(categoria);

        return categoria;
    }

    // Completado o método update que havia cortado
    public Categoria update(Long id, Categoria categoriaAtualizada) {

        Categoria categoriaExistente = findById(id);

        if (categoriaExistente != null) {
            categoriaExistente.setNome(categoriaAtualizada.getNome());
            return categoriaExistente;
        }

        return null;
    }

    // Completado o método delete seguindo o seu padrão
    public void delete(Long id) {
        listaCategorias.removeIf(categoria -> categoria.getId().equals(id));
    }
}