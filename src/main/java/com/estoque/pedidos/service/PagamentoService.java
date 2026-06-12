package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.estoque.pedidos.dto.request.PagamentoRequestDTO;
import com.estoque.pedidos.dto.response.PagamentoResponseDTO;
import com.estoque.pedidos.model.Pagamento;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.repository.PagamentoRepository;
import com.estoque.pedidos.repository.PedidoRepository;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;
    private final PedidoRepository pedidoRepository;

    public PagamentoService(
            PagamentoRepository repository,
            PedidoRepository pedidoRepository) {

        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<PagamentoResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    public PagamentoResponseDTO findById(Long id) {

        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pagamento não encontrado com o ID: " + id));

        return converteParaResponseDTO(pagamento);
    }

    public PagamentoResponseDTO save(PagamentoRequestDTO requestDTO) {

        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido não encontrado com o ID: "
                                        + requestDTO.pedidoId()));

        if (!"ABERTO".equalsIgnoreCase(pedido.getStatus())) {
            throw new RuntimeException(
                    "Só é permitido aprovar pagamento de pedido ABERTO.");
        }

        if (!pedido.getValorTotal().equals(requestDTO.valorPago())) {
            throw new RuntimeException(
                    "O valor pago deve ser igual ao valor total do pedido.");
        }

        Pagamento pagamento = new Pagamento();

        pagamento.setMetodoPagamento(requestDTO.metodoPagamento());
        pagamento.setDataConfirmacao(requestDTO.dataConfirmacao());
        pagamento.setStatusPagamento(requestDTO.statusPagamento());
        pagamento.setValorPago(requestDTO.valorPago());
        pagamento.setPedido(pedido);

        Pagamento pagamentoSalvo = repository.save(pagamento);

        pedido.setStatus("PAGO");
        pedidoRepository.save(pedido);

        return converteParaResponseDTO(pagamentoSalvo);
    }

    public PagamentoResponseDTO update(
            Long id,
            PagamentoRequestDTO requestDTO) {

        Pagamento pagamentoExistente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pagamento não encontrado com o ID: " + id));

        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido não encontrado com o ID: "
                                        + requestDTO.pedidoId()));

        if (!pedido.getValorTotal().equals(requestDTO.valorPago())) {
            throw new RuntimeException(
                    "O valor pago deve ser igual ao valor total do pedido.");
        }

        pagamentoExistente.setMetodoPagamento(
                requestDTO.metodoPagamento());

        pagamentoExistente.setDataConfirmacao(
                requestDTO.dataConfirmacao());

        pagamentoExistente.setStatusPagamento(
                requestDTO.statusPagamento());

        pagamentoExistente.setValorPago(
                requestDTO.valorPago());

        pagamentoExistente.setPedido(pedido);

        Pagamento pagamentoAtualizado =
                repository.save(pagamentoExistente);

        return converteParaResponseDTO(
                pagamentoAtualizado);
    }

    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException(
                    "Não é possível deletar. Pagamento não encontrado com o ID: "
                            + id);
        }

        repository.deleteById(id);
    }

    private PagamentoResponseDTO converteParaResponseDTO(
            Pagamento pagamento) {

        return new PagamentoResponseDTO(
                pagamento.getIdPagamento(),
                pagamento.getMetodoPagamento(),
                pagamento.getDataConfirmacao(),
                pagamento.getStatusPagamento(),
                pagamento.getValorPago(),
                pagamento.getPedido() != null
                        ? pagamento.getPedido().getIdPedido()
                        : null);
    }
}