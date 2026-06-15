package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.estoque.pedidos.dto.request.PedidoRequestDTO;
import com.estoque.pedidos.dto.response.PedidoResponseDTO;
import com.estoque.pedidos.exception.RegraNegocioException;
import com.estoque.pedidos.exception.ResourceNotFoundException;
import com.estoque.pedidos.mapper.PedidoMapper;
import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.model.enums.StatusPedido;
import com.estoque.pedidos.repository.ClienteRepository;
import com.estoque.pedidos.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final PedidoMapper mapper;

    public PedidoService(PedidoRepository repository, ClienteRepository clienteRepository, PedidoMapper mapper) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    public List<PedidoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o ID: " + id));
        return mapper.toResponseDTO(pedido);
    }

    public PedidoResponseDTO save(PedidoRequestDTO requestDTO) {
        // 1. Busca o cliente para garantir que ele existe
        Cliente cliente = clienteRepository.findById(requestDTO.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + requestDTO.clienteId()));

        // 2. Mapeia o DTO para a entidade (Data e Cliente)
        Pedido pedido = mapper.toEntity(requestDTO);
        pedido.setCliente(cliente);

        // 3. Regras de negócio de inicialização (Sistema define, não o usuário)
        pedido.setValorTotal(0.0);
        pedido.setStatus(StatusPedido.ABERTO);

        Pedido pedidoSalvo = repository.save(pedido);
        return mapper.toResponseDTO(pedidoSalvo);
    }

    public PedidoResponseDTO update(Long id, PedidoRequestDTO requestDTO) {
        Pedido pedidoExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o ID: " + id));

        Cliente cliente = clienteRepository.findById(requestDTO.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + requestDTO.clienteId()));

        mapper.updateEntityFromDTO(requestDTO, pedidoExistente);
        pedidoExistente.setCliente(cliente);

        Pedido pedidoAtualizado = repository.save(pedidoExistente);
        return mapper.toResponseDTO(pedidoAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    public PedidoResponseDTO cancelar(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o ID: " + id));

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraNegocioException("Apenas pedidos ABERTOs podem ser cancelados.");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        Pedido pedidoSalvo = repository.save(pedido);
        return mapper.toResponseDTO(pedidoSalvo);
    }
}