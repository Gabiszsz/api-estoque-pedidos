package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Pagamento;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.dto.request.PagamentoRequestDTO;
import com.estoque.pedidos.dto.response.PagamentoResponseDTO;
import com.estoque.pedidos.repository.PagamentoRepository;
import com.estoque.pedidos.repository.PedidoRepository;
import com.estoque.pedidos.mapper.PagamentoMapper; // Import adicionado

@Service
public class PagamentoService {

    private final PagamentoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoMapper mapper; // Declarado como final

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
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado com o ID: " + id));
        return mapper.toResponseDTO(pagamento);
    }

    public PagamentoResponseDTO save(PagamentoRequestDTO requestDTO) {
        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o ID: " + requestDTO.pedidoId()));

        Pagamento pagamento = mapper.toEntity(requestDTO);
        pagamento.setPedido(pedido); // Estabelece a relação ignorada pelo mapper estrutural

        Pagamento pagamentoSalvo = repository.save(pagamento);
        return mapper.toResponseDTO(pagamentoSalvo);
    }

    public PagamentoResponseDTO update(Long id, PagamentoRequestDTO requestDTO) {
        Pagamento pagamentoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado com o ID: " + id));

        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o ID: " + requestDTO.pedidoId()));

        mapper.updateEntityFromDTO(requestDTO, pagamentoExistente);
        pagamentoExistente.setPedido(pedido);

        Pagamento pagamentoAtualizado = repository.save(pagamentoExistente);
        return mapper.toResponseDTO(pagamentoAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar. Pagamento não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}