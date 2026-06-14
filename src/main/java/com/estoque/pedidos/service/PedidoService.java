package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import com.estoque.pedidos.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.dto.request.PedidoRequestDTO;
import com.estoque.pedidos.dto.response.PedidoResponseDTO;
import com.estoque.pedidos.repository.PedidoRepository;
import com.estoque.pedidos.repository.ClienteRepository;
import com.estoque.pedidos.mapper.PedidoMapper;
import com.estoque.pedidos.exception.ResourceNotFoundException;

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
        Cliente cliente = clienteRepository.findById(requestDTO.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + requestDTO.clienteId()));

        Pedido pedido = mapper.toEntity(requestDTO);
        pedido.setCliente(cliente);

        // Regra: Inicializa os dados automaticamente
        pedido.setValorTotal(0.0);
        pedido.setStatus("ABERTO");

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
    // Método chamado pelo PATCH no Controller
    public PedidoResponseDTO cancelar(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o ID: " + id));

        if (!"ABERTO".equalsIgnoreCase(pedido.getStatus())) {
            throw new RegraNegocioException("Apenas pedidos ABERTOs podem ser cancelados.");
        }

        pedido.setStatus("CANCELADO");
        Pedido pedidoSalvo = repository.save(pedido);
        return mapper.toResponseDTO(pedidoSalvo);
    }
}