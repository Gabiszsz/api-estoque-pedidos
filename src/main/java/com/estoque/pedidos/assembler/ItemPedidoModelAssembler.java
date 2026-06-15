package com.estoque.pedidos.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.estoque.pedidos.controller.ItemPedidoController;
import com.estoque.pedidos.controller.PedidoController;
import com.estoque.pedidos.controller.ProdutoController;
import com.estoque.pedidos.dto.response.ItemPedidoResponseDTO;

@Component
public class ItemPedidoModelAssembler implements RepresentationModelAssembler<ItemPedidoResponseDTO, EntityModel<ItemPedidoResponseDTO>> {

    @Override
    public EntityModel<ItemPedidoResponseDTO> toModel(ItemPedidoResponseDTO dto) {
        EntityModel<ItemPedidoResponseDTO> model = EntityModel.of(dto);

        // Self link
        model.add(linkTo(methodOn(ItemPedidoController.class)
                .buscarPorId(dto.id()))
                .withSelfRel());

        // Link para a listagem geral
        model.add(linkTo(methodOn(ItemPedidoController.class)
                .buscarTodos())
                .withRel("lista-itens-pedido"));

        // Referência cruzada para o Pedido pai
        if (dto.pedidoId() != null) {
            model.add(linkTo(methodOn(PedidoController.class)
                    .buscarPorId(dto.pedidoId()))
                    .withRel("consultar-pedido"));
        }

        // Referência cruzada para o Produto
        if (dto.produtoId() != null) {
            model.add(linkTo(methodOn(ProdutoController.class)
                    .buscarPorId(dto.produtoId()))
                    .withRel("consultar-produto"));
        }

        return model;
    }
}