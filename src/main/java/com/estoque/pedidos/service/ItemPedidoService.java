package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.ItemPedido;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.model.Produto;
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

        // Regra: Reserva de Estoque Imediata
        produto.baixarEstoque(requestDTO.quantidade());
        produtoRepository.save(produto);

        // NOVO: Puxa o preço direto do cadastro do Produto de forma segura
        Double precoUnitarioAtual = produto.getPreco().valor();

        ItemPedido item = mapper.toEntity(requestDTO);
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setPrecoUnitario(precoUnitarioAtual); // Congela o preço real na entidade

        // Regra: Cálculo dinâmico do Total
        pedido.setValorTotal(pedido.getValorTotal() + (item.getQuantidade() * item.getPrecoUnitario()));
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

        // Mantém o preço congelado original
        Double precoAplicado = itemExistente.getPrecoUnitario();
        Integer quantidadeAntiga = itemExistente.getQuantidade();

        // Se por acaso o usuário tentou trocar o produto do item...
        if (!produtoAtual.getId().equals(requestDTO.produtoId())) {
            Produto novoProduto = produtoRepository.findById(requestDTO.produtoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + requestDTO.produtoId()));

            // Devolve o estoque do produto antigo
            produtoAtual.adicionarEstoque(quantidadeAntiga);
            produtoRepository.save(produtoAtual);

            // Baixa o estoque do novo produto
            novoProduto.baixarEstoque(requestDTO.quantidade());
            produtoRepository.save(novoProduto);

            produtoAtual = novoProduto;
            precoAplicado = novoProduto.getPreco().valor(); // Atualiza o preço para o do novo produto
        } else {
            // Se for o mesmo produto, resolve apenas a diferença de estoque
            int diferenca = requestDTO.quantidade() - quantidadeAntiga;
            if (diferenca > 0) {
                produtoAtual.baixarEstoque(diferenca);
            } else if (diferenca < 0) {
                produtoAtual.adicionarEstoque(Math.abs(diferenca));
            }
            produtoRepository.save(produtoAtual);
        }

        // 2. Resolve a diferença financeira
        Double valorAntigoFinanceiro = quantidadeAntiga * itemExistente.getPrecoUnitario();
        Double valorNovoFinanceiro = requestDTO.quantidade() * precoAplicado;

        pedido.setValorTotal(pedido.getValorTotal() - valorAntigoFinanceiro + valorNovoFinanceiro);
        pedidoRepository.save(pedido);

        // Atualiza a entidade de forma limpa
        mapper.updateEntityFromDTO(requestDTO, itemExistente);
        itemExistente.setProduto(produtoAtual);
        itemExistente.setPrecoUnitario(precoAplicado); // Salva o preço correto

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