package com.estoque.pedidos.mother;

import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.model.vo.Preco;

public class ProdutoMother {

    public static Produto criarTecladoValido() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Teclado");
        produto.setQuantidadeEstoque(10);
        produto.setPreco(new Preco(150.0, "BRL"));
        return produto;
    }

    public static Produto criarMouseSemEstoque() {
        Produto produto = new Produto();
        produto.setId(2L);
        produto.setNome("Mouse Gamer");
        produto.setQuantidadeEstoque(0);
        produto.setPreco(new Preco(80.0, "BRL"));
        return produto;
    }

}