package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.ItemPedido;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.ItemPedidoRequestDTO;
import com.estoque.pedidos.dto.response.ItemPedidoResponseDTO;
import com.estoque.pedidos.repository.ItemPedidoRepository;
import com.estoque.pedidos.repository.PedidoRepository;
import com.estoque.pedidos.repository.ProdutoRepository;
import com.estoque.pedidos.mapper.ItemPedidoMapper; // Import adicionado

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoMapper mapper; // Declarado como final

    public ItemPedidoService(ItemPedidoRepository repository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, ItemPedidoMapper mapper) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.mapper = mapper;
    }

    public List<ItemPedidoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemPedidoResponseDTO findById(Long id) {
        ItemPedido item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemPedido não encontrado com o ID: " + id));
        return mapper.toResponseDTO(item);
    }

    public ItemPedidoResponseDTO save(ItemPedidoRequestDTO requestDTO) {
        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ItemPedido item = mapper.toEntity(requestDTO);
        item.setPedido(pedido);
        item.setProduto(produto); // Conecta os relacionamentos buscados separadamente

        ItemPedido itemSalvo = repository.save(item);
        return mapper.toResponseDTO(itemSalvo);
    }

    public ItemPedidoResponseDTO update(Long id, ItemPedidoRequestDTO requestDTO) {
        ItemPedido itemExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemPedido não encontrado"));

        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        mapper.updateEntityFromDTO(requestDTO, itemExistente);
        itemExistente.setPedido(pedido);
        itemExistente.setProduto(produto);

        ItemPedido itemAtualizado = repository.save(itemExistente);
        return mapper.toResponseDTO(itemAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("ItemPedido não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}