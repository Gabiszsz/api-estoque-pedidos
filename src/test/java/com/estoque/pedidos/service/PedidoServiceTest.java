package com.estoque.pedidos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.estoque.pedidos.dto.request.PedidoRequestDTO;
import com.estoque.pedidos.dto.response.PedidoResponseDTO;
import com.estoque.pedidos.exception.RegraNegocioException;
import com.estoque.pedidos.mapper.PedidoMapper;
import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.repository.ClienteRepository;
import com.estoque.pedidos.repository.PedidoRepository;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PedidoMapper mapper;

    private Pedido pedidoMock;
    private Cliente clienteMock;

    @BeforeEach
    void setUp() {
        // Preparamos um cenário básico antes de cada teste
        clienteMock = new Cliente();
        clienteMock.setId(1L);

        pedidoMock = new Pedido();
        pedidoMock.setIdPedido(1L);
        pedidoMock.setStatus("ABERTO"); // Status inicial padrão
        pedidoMock.setValorTotal(100.0);
    }

    @Test
    @DisplayName("Deve inicializar o pedido com status ABERTO e valor 0.0 ignorando o DTO")
    void deveSalvarPedidoComStatusAbertoEValorZero() {
        // 1. Arrange: O utilizador mal-intencionado tentou enviar um DTO já como "PAGO" e com valor 5000.0
        PedidoRequestDTO requestDTO = new PedidoRequestDTO(LocalDate.now(), "PAGO", 5000.0, 1L);
        Pedido pedidoMapeado = new Pedido();
        pedidoMapeado.setStatus(requestDTO.status());
        pedidoMapeado.setValorTotal(requestDTO.valorTotal());

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(mapper.toEntity(requestDTO)).thenReturn(pedidoMapeado);
        when(pedidoRepository.save(any())).thenAnswer(i -> i.getArguments()[0]); // Retorna o mesmo objecto que tentou salvar
        when(mapper.toResponseDTO(any())).thenAnswer(i -> {
            Pedido p = i.getArgument(0);
            return new PedidoResponseDTO(1L, p.getDataPedido(), p.getStatus(), p.getValorTotal(), null, null);
        });

        // 2. Act
        PedidoResponseDTO response = pedidoService.save(requestDTO);

        // 3. Assert: Garantimos que o Service corrigiu a tentativa de fraude
        assertNotNull(response);
        assertEquals("ABERTO", response.status(), "O status deve ser forçado a ABERTO na criação");
        assertEquals(0.0, response.valorTotal(), "O valor total deve ser forçado a 0.0 na criação");
    }

    @Test
    @DisplayName("Deve cancelar um pedido com sucesso se ele estiver ABERTO")
    void deveCancelarPedidoAberto() {
        // 1. Arrange: O pedido já está ABERTO no setUp()
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoMock));
        when(pedidoRepository.save(any())).thenReturn(pedidoMock);

        // Configuramos o mapper para retornar o DTO com o status atualizado do pedidoMock
        when(mapper.toResponseDTO(any())).thenAnswer(i -> {
            Pedido p = i.getArgument(0);
            return new PedidoResponseDTO(p.getIdPedido(), p.getDataPedido(), p.getStatus(), p.getValorTotal(), null, null);
        });

        // 2. Act
        PedidoResponseDTO response = pedidoService.cancelar(1L);

        // 3. Assert
        assertEquals("CANCELADO", response.status());
        verify(pedidoRepository, times(1)).save(pedidoMock);
    }

    @Test
    @DisplayName("Deve lançar RegraNegocioException ao tentar cancelar um pedido que já está PAGO")
    void deveLancarExcecaoAoCancelarPedidoNaoAberto() {
        // 1. Arrange: Mudamos o estado do nosso pedido falso para PAGO
        pedidoMock.setStatus("PAGO");
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoMock));

        // 2 e 3. Act & Assert: Usamos assertThrows para capturar a exceção esperada
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> {
            pedidoService.cancelar(1L);
        });

        // Garantimos que a mensagem de erro é a correta
        assertEquals("Apenas pedidos ABERTOs podem ser cancelados.", exception.getMessage());

        // Muito Importante: Garantimos que o repositório NUNCA foi chamado para salvar
        verify(pedidoRepository, never()).save(any());
    }
}