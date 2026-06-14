package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import com.estoque.pedidos.exception.RegraNegocioException;
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
                .orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new RegraNegocioException("Produto não encontrado"));

        // Regra: Reserva de Estoque Imediata (O método baixarEstoque já lança erro se não tiver)
        produto.baixarEstoque(requestDTO.quantidade());
        produtoRepository.save(produto); // Salva o novo estoque

        ItemPedido item = mapper.toEntity(requestDTO);
        item.setPedido(pedido);
        item.setProduto(produto);

        // Regra: Cálculo dinâmico do Total
        pedido.setValorTotal(pedido.getValorTotal() + (item.getQuantidade() * item.getPrecoUnitario()));
        pedidoRepository.save(pedido);

        ItemPedido itemSalvo = repository.save(item);
        return mapper.toResponseDTO(itemSalvo);
    }

    public ItemPedidoResponseDTO update(Long id, ItemPedidoRequestDTO requestDTO) {
        ItemPedido itemExistente = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("ItemPedido não encontrado"));

        Produto produto = itemExistente.getProduto();
        Pedido pedido = itemExistente.getPedido();

        // 1. Resolve a diferença de estoque do "Efeito Colateral"
        int diferenca = requestDTO.quantidade() - itemExistente.getQuantidade();
        if (diferenca > 0) {
            produto.baixarEstoque(diferenca); // Exigiu mais, baixa do estoque
        } else if (diferenca < 0) {
            produto.adicionarEstoque(Math.abs(diferenca)); // Exigiu menos, devolve ao estoque
        }
        produtoRepository.save(produto);

        // 2. Resolve a diferença financeira
        Double valorAntigo = itemExistente.getQuantidade() * itemExistente.getPrecoUnitario();
        Double valorNovo = requestDTO.quantidade() * requestDTO.precoUnitario();
        pedido.setValorTotal(pedido.getValorTotal() - valorAntigo + valorNovo);
        pedidoRepository.save(pedido);

        mapper.updateEntityFromDTO(requestDTO, itemExistente);
        ItemPedido itemAtualizado = repository.save(itemExistente);

        return mapper.toResponseDTO(itemAtualizado);
    }

    public void delete(Long id) {
        ItemPedido itemExistente = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("ItemPedido não encontrado com o ID: " + id));

        // Devolve ao estoque
        Produto produto = itemExistente.getProduto();
        produto.adicionarEstoque(itemExistente.getQuantidade());
        produtoRepository.save(produto);

        // Retira do valor total do pedido
        Pedido pedido = itemExistente.getPedido();
        pedido.setValorTotal(pedido.getValorTotal() - (itemExistente.getQuantidade() * itemExistente.getPrecoUnitario()));
        pedidoRepository.save(pedido);

        repository.deleteById(id);
    }
}