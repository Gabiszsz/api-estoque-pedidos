package com.estoque.pedidos.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.estoque.pedidos.controller.PagamentoController;
import com.estoque.pedidos.controller.PedidoController;
import com.estoque.pedidos.dto.response.PagamentoResponseDTO;

@Component
public class PagamentoModelAssembler implements RepresentationModelAssembler<PagamentoResponseDTO, EntityModel<PagamentoResponseDTO>> {

    @Override
    public EntityModel<PagamentoResponseDTO> toModel(PagamentoResponseDTO dto) {
        EntityModel<PagamentoResponseDTO> model = EntityModel.of(dto);

        // Self Link
        model.add(linkTo(methodOn(PagamentoController.class)
                .buscarPorId(dto.idPagamento()))
                .withSelfRel());

        // Lista geral de pagamentos
        model.add(linkTo(methodOn(PagamentoController.class)
                .buscarTodos())
                .withRel("lista-pagamentos"));

        // Link de Referência Cruzada: Permite navegar direto para o Pedido pago
        if (dto.pedidoId() != null) {
            model.add(linkTo(methodOn(PedidoController.class)
                    .buscarPorId(dto.pedidoId()))
                    .withRel("consultar-pedido"));
        }

        return model;
    }
}