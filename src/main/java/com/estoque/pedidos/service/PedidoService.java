package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.estoque.pedidos.dto.request.PedidoRequestDTO;
import com.estoque.pedidos.dto.response.ClienteResponseDTO;
import com.estoque.pedidos.dto.response.PedidoResponseDTO;
import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.repository.ClienteRepository;
import com.estoque.pedidos.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    public List<PedidoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o ID: " + id));
        return converteParaResponseDTO(pedido);
    }

    public PedidoResponseDTO save(PedidoRequestDTO requestDTO) {

    Cliente cliente = clienteRepository.findById(requestDTO.clienteId())
            .orElseThrow(() ->
                    new RuntimeException(
                            "Cliente não encontrado com o ID: "
                                    + requestDTO.clienteId()));

    Pedido pedido = new Pedido();

    pedido.setDataPedido(requestDTO.dataPedido());
    pedido.setStatus(requestDTO.status());

    // valor calculado automaticamente pelos itens
    pedido.setValorTotal(0.0);

    pedido.setCliente(cliente);

    Pedido pedidoSalvo = repository.save(pedido);

    return converteParaResponseDTO(pedidoSalvo);
}

    public PedidoResponseDTO update(
        Long id,
        PedidoRequestDTO requestDTO) {

    Pedido pedidoExistente = repository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Pedido não encontrado com o ID: " + id));

    Cliente cliente = clienteRepository.findById(
            requestDTO.clienteId())
            .orElseThrow(() ->
                    new RuntimeException(
                            "Cliente não encontrado com o ID: "
                                    + requestDTO.clienteId()));

    pedidoExistente.setDataPedido(
            requestDTO.dataPedido());

    pedidoExistente.setStatus(
            requestDTO.status());

    pedidoExistente.setCliente(cliente);

    Pedido pedidoAtualizado =
            repository.save(pedidoExistente);

    return converteParaResponseDTO(
            pedidoAtualizado);
}
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Pedido não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private PedidoResponseDTO converteParaResponseDTO(Pedido pedido) {
        ClienteResponseDTO clienteDTO = null;
        if (pedido.getCliente() != null) {
            clienteDTO = new ClienteResponseDTO(
                    pedido.getCliente().getId(),
                    pedido.getCliente().getCpf(),
                    pedido.getCliente().getEnderecoCompleto()
            );
        }
        return new PedidoResponseDTO(
                pedido.getIdPedido(),
                pedido.getDataPedido(),
                pedido.getStatus(),
                pedido.getValorTotal(),
                clienteDTO
        );
    }
}