package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.estoque.pedidos.dto.request.PagamentoRequestDTO;
import com.estoque.pedidos.dto.response.PagamentoResponseDTO;
import com.estoque.pedidos.exception.RegraNegocioException;
import com.estoque.pedidos.exception.ResourceNotFoundException;
import com.estoque.pedidos.mapper.PagamentoMapper;
import com.estoque.pedidos.model.Pagamento;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.model.enums.StatusPagamento;
import com.estoque.pedidos.model.enums.StatusPedido;
import com.estoque.pedidos.repository.PagamentoRepository;
import com.estoque.pedidos.repository.PedidoRepository;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoMapper mapper;

    public PagamentoService(PagamentoRepository repository, PedidoRepository pedidoRepository, PagamentoMapper mapper) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
        this.mapper = mapper;
    }

    public List<PagamentoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PagamentoResponseDTO findById(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado com o ID: " + id));
        return mapper.toResponseDTO(pagamento);
    }

    public PagamentoResponseDTO save(PagamentoRequestDTO requestDTO) {
        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o ID: " + requestDTO.pedidoId()));

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraNegocioException("Só é possível efetuar o pagamento de um pedido com status ABERTO.");
        }

        // Validação segura com BigDecimal ignorando escalas diferentes (ex: 100.00 == 100.0)
        if (pedido.getValorTotal().compareTo(requestDTO.valorPago()) != 0) {
            throw new RegraNegocioException(String.format(
                    "O valor pago (%s) diverge do valor total do pedido (%s).",
                    requestDTO.valorPago(), pedido.getValorTotal()
            ));
        }

        Pagamento pagamento = mapper.toEntity(requestDTO);
        pagamento.setPedido(pedido);
        pagamento.setStatusPagamento(StatusPagamento.CONFIRMADO);

        pedido.setStatus(StatusPedido.PAGO);
        pedidoRepository.save(pedido);

        Pagamento pagamentoSalvo = repository.save(pagamento);
        return mapper.toResponseDTO(pagamentoSalvo);
    }

    public PagamentoResponseDTO update(Long id, PagamentoRequestDTO requestDTO) {
        Pagamento pagamentoExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado com o ID: " + id));

        mapper.updateEntityFromDTO(requestDTO, pagamentoExistente);

        Pagamento pagamentoAtualizado = repository.save(pagamentoExistente);
        return mapper.toResponseDTO(pagamentoAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Não é possível deletar. Pagamento não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}