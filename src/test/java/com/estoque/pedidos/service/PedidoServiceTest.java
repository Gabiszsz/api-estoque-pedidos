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
import com.estoque.pedidos.model.enums.StatusPedido;
import com.estoque.pedidos.mother.ClienteMother;
import com.estoque.pedidos.mother.PedidoMother;
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
        clienteMock = ClienteMother.criarClienteValido();
        pedidoMock = PedidoMother.criarPedidoAberto();
    }

    @Test
    @DisplayName("Deve inicializar o pedido com status ABERTO e valor 0.0 ignorando o DTO")
    void deveSalvarPedidoComStatusAbertoEValorZero() {
        // Arrange: DTO agora apenas com data e ID do cliente
        PedidoRequestDTO requestDTO = new PedidoRequestDTO(LocalDate.now(), 1L);
        Pedido pedidoMapeado = new Pedido();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(mapper.toEntity(requestDTO)).thenReturn(pedidoMapeado);
        when(pedidoRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(mapper.toResponseDTO(any())).thenAnswer(i -> {
            Pedido p = i.getArgument(0);
            return new PedidoResponseDTO(1L, p.getDataPedido(), p.getStatus(), p.getValorTotal(), null, null);
        });

        // Act
        PedidoResponseDTO response = pedidoService.save(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(StatusPedido.ABERTO, response.status(), "O status deve ser forçado a ABERTO na criação");
        assertEquals(0.0, response.valorTotal(), "O valor total deve ser forçado a 0.0 na criação");
    }

    @Test
    @DisplayName("Deve cancelar um pedido com sucesso se ele estiver ABERTO")
    void deveCancelarPedidoAberto() {
        // Arrange
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoMock));
        when(pedidoRepository.save(any())).thenReturn(pedidoMock);

        when(mapper.toResponseDTO(any())).thenAnswer(i -> {
            Pedido p = i.getArgument(0);
            return new PedidoResponseDTO(p.getIdPedido(), p.getDataPedido(), p.getStatus(), p.getValorTotal(), null, null);
        });

        // Act
        PedidoResponseDTO response = pedidoService.cancelar(1L);

        // Assert
        assertEquals(StatusPedido.CANCELADO, response.status());
        verify(pedidoRepository, times(1)).save(pedidoMock);
    }

    @Test
    @DisplayName("Deve lançar RegraNegocioException ao tentar cancelar um pedido que já está PAGO")
    void deveLancarExcecaoAoCancelarPedidoNaoAberto() {
        // Arrange
        pedidoMock.setStatus(StatusPedido.PAGO);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoMock));

        // Act & Assert
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> {
            pedidoService.cancelar(1L);
        });

        assertEquals("Apenas pedidos ABERTOs podem ser cancelados.", exception.getMessage());
        verify(pedidoRepository, never()).save(any());
    }
}