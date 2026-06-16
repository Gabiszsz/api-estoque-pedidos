package com.estoque.pedidos.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.ItemPedido;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.model.vo.Preco;
import com.estoque.pedidos.dto.request.ItemPedidoRequestDTO;
import com.estoque.pedidos.dto.response.ItemPedidoResponseDTO;
import com.estoque.pedidos.repository.ItemPedidoRepository;
import com.estoque.pedidos.repository.PedidoRepository;
import com.estoque.pedidos.repository.ProdutoRepository;
import com.estoque.pedidos.mapper.ItemPedidoMapper;
import com.estoque.pedidos.exception.ResourceNotFoundException;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoMapper mapper;

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
                .orElseThrow(() -> new ResourceNotFoundException("ItemPedido não encontrado com o ID: " + id));
        return mapper.toResponseDTO(item);
    }

    @Caching(evict = {
            @CacheEvict(value = "listaProdutos", allEntries = true),
            @CacheEvict(value = "produtoUnico", allEntries = true)
    })
    public ItemPedidoResponseDTO save(ItemPedidoRequestDTO requestDTO) {
        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o ID: " + requestDTO.pedidoId()));
        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + requestDTO.produtoId()));

        Optional<ItemPedido> itemExistenteOpt = repository.findByPedido_IdPedidoAndProduto_Id(requestDTO.pedidoId(), requestDTO.produtoId());

        if (itemExistenteOpt.isPresent()) {
            ItemPedido itemExistente = itemExistenteOpt.get();

            produto.baixarEstoque(requestDTO.quantidade());
            produtoRepository.save(produto);

            itemExistente.setQuantidade(itemExistente.getQuantidade() + requestDTO.quantidade());

            // Calcula o acréscimo de valor com BigDecimal
            BigDecimal valorAdicional = itemExistente.getPrecoUnitario().valor().multiply(BigDecimal.valueOf(requestDTO.quantidade()));
            pedido.setValorTotal(pedido.getValorTotal().add(valorAdicional));
            pedidoRepository.save(pedido);

            ItemPedido itemSalvo = repository.save(itemExistente);
            return mapper.toResponseDTO(itemSalvo);
        }

        produto.baixarEstoque(requestDTO.quantidade());
        produtoRepository.save(produto);

        // Clona o VO Preço do Produto no exato momento da compra
        Preco precoNoMomento = new Preco(produto.getPreco().valor(), produto.getPreco().moeda());

        ItemPedido item = mapper.toEntity(requestDTO);
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setPrecoUnitario(precoNoMomento);

        BigDecimal valorDoItem = item.getPrecoUnitario().valor().multiply(BigDecimal.valueOf(item.getQuantidade()));
        pedido.setValorTotal(pedido.getValorTotal().add(valorDoItem));
        pedidoRepository.save(pedido);

        ItemPedido itemSalvo = repository.save(item);
        return mapper.toResponseDTO(itemSalvo);
    }

    @Caching(evict = {
            @CacheEvict(value = "listaProdutos", allEntries = true),
            @CacheEvict(value = "produtoUnico", allEntries = true)
    })
    public ItemPedidoResponseDTO update(Long id, ItemPedidoRequestDTO requestDTO) {
        ItemPedido itemExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemPedido não encontrado com o ID: " + id));

        Produto produtoAtual = itemExistente.getProduto();
        Pedido pedido = itemExistente.getPedido();

        Preco precoAplicado = new Preco(itemExistente.getPrecoUnitario().valor(), itemExistente.getPrecoUnitario().moeda());
        Integer quantidadeAntiga = itemExistente.getQuantidade();

        BigDecimal valorAntigoFinanceiro = precoAplicado.valor().multiply(BigDecimal.valueOf(quantidadeAntiga));

        if (!produtoAtual.getId().equals(requestDTO.produtoId())) {
            Produto novoProduto = produtoRepository.findById(requestDTO.produtoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + requestDTO.produtoId()));

            produtoAtual.adicionarEstoque(quantidadeAntiga);
            produtoRepository.save(produtoAtual);

            novoProduto.baixarEstoque(requestDTO.quantidade());
            produtoRepository.save(novoProduto);

            produtoAtual = novoProduto;
            precoAplicado = new Preco(novoProduto.getPreco().valor(), novoProduto.getPreco().moeda());
        } else {
            int diferenca = requestDTO.quantidade() - quantidadeAntiga;
            if (diferenca > 0) {
                produtoAtual.baixarEstoque(diferenca);
            } else if (diferenca < 0) {
                produtoAtual.adicionarEstoque(Math.abs(diferenca));
            }
            produtoRepository.save(produtoAtual);
        }

        BigDecimal valorNovoFinanceiro = precoAplicado.valor().multiply(BigDecimal.valueOf(requestDTO.quantidade()));

        pedido.setValorTotal(pedido.getValorTotal().subtract(valorAntigoFinanceiro).add(valorNovoFinanceiro));
        pedidoRepository.save(pedido);

        mapper.updateEntityFromDTO(requestDTO, itemExistente);
        itemExistente.setProduto(produtoAtual);
        itemExistente.setPrecoUnitario(precoAplicado);

        ItemPedido itemAtualizado = repository.save(itemExistente);
        return mapper.toResponseDTO(itemAtualizado);
    }

    @Caching(evict = {
            @CacheEvict(value = "listaProdutos", allEntries = true),
            @CacheEvict(value = "produtoUnico", allEntries = true)
    })
    public void delete(Long id) {
        ItemPedido itemExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemPedido não encontrado com o ID: " + id));

        Produto produto = itemExistente.getProduto();
        produto.adicionarEstoque(itemExistente.getQuantidade());
        produtoRepository.save(produto);

        Pedido pedido = itemExistente.getPedido();
        BigDecimal valorRetirado = itemExistente.getPrecoUnitario().valor().multiply(BigDecimal.valueOf(itemExistente.getQuantidade()));
        pedido.setValorTotal(pedido.getValorTotal().subtract(valorRetirado));
        pedidoRepository.save(pedido);

        repository.deleteById(id);
    }
}