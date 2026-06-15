package com.estoque.pedidos.model;

import com.estoque.pedidos.exception.EstoqueInsuficienteException;
import com.estoque.pedidos.mother.ProdutoMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    @DisplayName("Deve baixar o estoque com sucesso quando há saldo suficiente")
    void deveBaixarEstoqueComSucesso() {
        // Arrange
        Produto produto = ProdutoMother.criarTecladoValido(); // Já vem instanciado, com nome "Teclado" e 10 no estoque

        // Act
        produto.baixarEstoque(3); // Tentamos vender 3

        // Assert
        assertEquals(7, produto.getQuantidadeEstoque()); // Deve sobrar 7
    }

    @Test
    @DisplayName("Deve lançar EstoqueInsuficienteException ao tentar baixar mais do que o saldo")
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        // Arrange
        Produto produto = ProdutoMother.criarTecladoValido();
        produto.setQuantidadeEstoque(5); // Sobrescrevemos o estoque para 5 apenas para forçar este cenário de erro

        // Act & Assert (AssertThrows verifica se a exceção correta foi disparada)
        EstoqueInsuficienteException exception = assertThrows(EstoqueInsuficienteException.class, () -> {
            produto.baixarEstoque(6); // Tentamos vender 6 (vai explodir)
        });

        assertEquals("Estoque insuficiente para o produto: Teclado", exception.getMessage());
        assertEquals(5, produto.getQuantidadeEstoque()); // O estoque deve continuar intacto
    }
}