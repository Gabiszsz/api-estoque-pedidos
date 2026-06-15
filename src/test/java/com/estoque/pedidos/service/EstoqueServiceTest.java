package com.estoque.pedidos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.estoque.pedidos.dto.request.EstoqueRequestDTO;
import com.estoque.pedidos.dto.response.EstoqueResponseDTO;
import com.estoque.pedidos.mapper.EstoqueMapper;
import com.estoque.pedidos.model.Estoque;
import com.estoque.pedidos.model.Fornecedor;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.repository.EstoqueRepository;
import com.estoque.pedidos.repository.FornecedorRepository;
import com.estoque.pedidos.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @InjectMocks private EstoqueService estoqueService;
    @Mock private EstoqueRepository estoqueRepository;
    @Mock private ProdutoRepository produtoRepository;
    @Mock private FornecedorRepository fornecedorRepository;
    @Mock private EstoqueMapper mapper;

    @Test
    @DisplayName("Deve salvar o lote de estoque e somar a quantidade automaticamente no Produto global")
    void deveSalvarEstoqueESomarNoProduto() {
        // 1. Arrange
        Produto produtoMock = new Produto();
        produtoMock.setId(1L);
        produtoMock.setQuantidadeEstoque(10); // Produto já tinha 10 unidades

        Fornecedor fornecedorMock = new Fornecedor();
        fornecedorMock.setId(1L);

        // Chegou uma nota fiscal com mais 50 unidades
        EstoqueRequestDTO requestDTO = new EstoqueRequestDTO(50, 15, "Prateleira A", 1L, 1L, "NF-123");
        Estoque estoqueMapeado = new Estoque();

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoMock));
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedorMock));
        when(mapper.toEntity(requestDTO)).thenReturn(estoqueMapeado);
        when(estoqueRepository.save(any())).thenReturn(estoqueMapeado);
        when(mapper.toResponseDTO(any())).thenReturn(new EstoqueResponseDTO(1L, 50, 15, "Prateleira A", null));

        // 2. Act
        estoqueService.save(requestDTO);

        // 3. Assert (A Prova Real)
        // O estoque total do produto DEVE ter subido para 60 (10 que tinha + 50 que chegou)
        assertEquals(60, produtoMock.getQuantidadeEstoque());

        // Verifica se o ProdutoRepository foi chamado para salvar essa nova quantidade
        verify(produtoRepository, times(1)).save(produtoMock);

        // Verifica se salvou a data da entrada
        assertNotNull(estoqueMapeado.getDataEntrada());
    }
}