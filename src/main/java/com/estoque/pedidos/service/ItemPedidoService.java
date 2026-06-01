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

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoService(ItemPedidoRepository repository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<ItemPedidoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemPedidoResponseDTO findById(Long id) {
        ItemPedido item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemPedido não encontrado com o ID: " + id));
        return converteParaResponseDTO(item);
    }

    public ItemPedidoResponseDTO save(ItemPedidoRequestDTO requestDTO) {
        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ItemPedido item = new ItemPedido();
        item.setQuantidade(requestDTO.quantidade());
        item.setPrecoUnitario(requestDTO.precoUnitario());
        item.setPedido(pedido);
        item.setProduto(produto);

        ItemPedido itemSalvo = repository.save(item);
        return converteParaResponseDTO(itemSalvo);
    }

    public ItemPedidoResponseDTO update(Long id, ItemPedidoRequestDTO requestDTO) {
        ItemPedido itemExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemPedido não encontrado"));

        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        itemExistente.setQuantidade(requestDTO.quantidade());
        itemExistente.setPrecoUnitario(requestDTO.precoUnitario());
        itemExistente.setPedido(pedido);
        itemExistente.setProduto(produto);

        ItemPedido itemAtualizado = repository.save(itemExistente);
        return converteParaResponseDTO(itemAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("ItemPedido não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private ItemPedidoResponseDTO converteParaResponseDTO(ItemPedido item) {
        return new ItemPedidoResponseDTO(
                item.getId(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getPedido() != null ? item.getPedido().getIdPedido() : null,
                item.getProduto() != null ? item.getProduto().getId() : null
        );
    }
}