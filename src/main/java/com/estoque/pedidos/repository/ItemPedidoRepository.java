package com.estoque.pedidos.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component; // Importação alterada para Component

import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.model.ItemPedido;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.model.Produto;

@Component // Alterado de @Repository para @Component para remover o aviso amarelo
public class ItemPedidoRepository {

    // Adicionado o 'final' para remover o aviso de boa prática do VS Code
    private final List<ItemPedido> listaItens = new ArrayList<>();

    public ItemPedidoRepository() {

        Cliente cliente = new Cliente(
            1L,
            "111.111.111-11",
            "Rua A"
        );

        Pedido pedido = new Pedido(
            1L,
            LocalDate.now(),
            "ABERTO",
            4500.0,
            cliente
        );

        Produto produto = new Produto(
            1L,
            "PROD001",
            "Notebook Gamer",
            4500.0,
            "UN",
            10
        );

        listaItens.add(
            new ItemPedido(
                1L,
                1,
                4500.0,
                pedido,
                produto
            )
        );
    }

    public List<ItemPedido> findAll() {
        return listaItens;
    }

    public ItemPedido findById(Long id) {

        for (ItemPedido item : listaItens) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    public ItemPedido save(ItemPedido itemPedido) {

        itemPedido.setId((long) (listaItens.size() + 1));

        listaItens.add(itemPedido);

        return itemPedido;
    }

    public ItemPedido update(Long id, ItemPedido itemAtualizado) {

        ItemPedido itemExistente = findById(id);

        if (itemExistente != null) {

            itemExistente.setQuantidade(itemAtualizado.getQuantidade());
            itemExistente.setPrecoUnitario(itemAtualizado.getPrecoUnitario());
            itemExistente.setPedido(itemAtualizado.getPedido());
            itemExistente.setProduto(itemAtualizado.getProduto());

            return itemExistente;
        }

        return null;
    }

    public void delete(Long id) {

        listaItens.removeIf(item -> item.getId().equals(id));
    }
}