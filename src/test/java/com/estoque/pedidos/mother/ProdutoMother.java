package com.estoque.pedidos.mother;

import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.model.vo.Preco;
import java.math.BigDecimal;

public class ProdutoMother {

    public static Produto criarTecladoValido() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Teclado");
        produto.setQuantidadeEstoque(10);
        produto.setPreco(new Preco(new BigDecimal("150.00"), "BRL"));
        return produto;
    }

    public static Produto criarMouseSemEstoque() {
        Produto produto = new Produto();
        produto.setId(2L);
        produto.setNome("Mouse Gamer");
        produto.setQuantidadeEstoque(0);
        produto.setPreco(new Preco(new BigDecimal("80.00"), "BRL"));
        return produto;
    }

}