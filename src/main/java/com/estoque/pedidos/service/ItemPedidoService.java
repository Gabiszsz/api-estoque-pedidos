package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.estoque.pedidos.dto.request.ItemPedidoRequestDTO;
import com.estoque.pedidos.dto.response.ItemPedidoResponseDTO;
import com.estoque.pedidos.model.ItemPedido;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.repository.ItemPedidoRepository;
import com.estoque.pedidos.repository.PedidoRepository;
import com.estoque.pedidos.repository.ProdutoRepository;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoService(
            ItemPedidoRepository repository,
            PedidoRepository pedidoRepository,
            ProdutoRepository produtoRepository) {

        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<ItemPedidoResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::converteParaResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemPedidoResponseDTO findById(Long id) {

        ItemPedido item = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "ItemPedido não encontrado com o ID: " + id));

        return converteParaResponseDTO(item);
    }

    public ItemPedidoResponseDTO save(ItemPedidoRequestDTO requestDTO) {

        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() ->
                        new RuntimeException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        if (requestDTO.quantidade() <= 0) {
            throw new RuntimeException(
                    "A quantidade deve ser maior que zero.");
        }

        produto.baixarEstoque(requestDTO.quantidade());
        produtoRepository.save(produto);

        ItemPedido item = new ItemPedido();
        item.setQuantidade(requestDTO.quantidade());
        item.setPrecoUnitario(requestDTO.precoUnitario());
        item.setPedido(pedido);
        item.setProduto(produto);

        ItemPedido itemSalvo = repository.save(item);

        recalcularValorTotalPedido(pedido);

        return converteParaResponseDTO(itemSalvo);
    }

    public ItemPedidoResponseDTO update(
            Long id,
            ItemPedidoRequestDTO requestDTO) {

        ItemPedido itemExistente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ItemPedido não encontrado"));

        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() ->
                        new RuntimeException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        itemExistente.setQuantidade(requestDTO.quantidade());
        itemExistente.setPrecoUnitario(requestDTO.precoUnitario());
        itemExistente.setPedido(pedido);
        itemExistente.setProduto(produto);

        ItemPedido itemAtualizado = repository.save(itemExistente);

        recalcularValorTotalPedido(pedido);

        return converteParaResponseDTO(itemAtualizado);
    }

    public void delete(Long id) {

        ItemPedido item = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "ItemPedido não encontrado com o ID: " + id));

        Pedido pedido = item.getPedido();

        repository.delete(item);

        recalcularValorTotalPedido(pedido);
    }

    private void recalcularValorTotalPedido(Pedido pedido) {

        List<ItemPedido> itens =
                repository.findByPedidoIdPedido(
                        pedido.getIdPedido());

        double total = itens.stream()
                .mapToDouble(i ->
                        i.getQuantidade()
                                * i.getPrecoUnitario())
                .sum();

        pedido.setValorTotal(total);

        pedidoRepository.save(pedido);
    }

    private ItemPedidoResponseDTO converteParaResponseDTO(
            ItemPedido item) {

        return new ItemPedidoResponseDTO(
                item.getId(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getPedido() != null
                        ? item.getPedido().getIdPedido()
                        : null,
                item.getProduto() != null
                        ? item.getProduto().getId()
                        : null);
    }
}