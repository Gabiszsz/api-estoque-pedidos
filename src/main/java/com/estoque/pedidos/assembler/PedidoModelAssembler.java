package com.estoque.pedidos.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.estoque.pedidos.controller.PedidoController;
import com.estoque.pedidos.controller.PagamentoController;
import com.estoque.pedidos.dto.response.PedidoResponseDTO;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<PedidoResponseDTO, EntityModel<PedidoResponseDTO>> {

    @Override
    public EntityModel<PedidoResponseDTO> toModel(PedidoResponseDTO dto) {
        EntityModel<PedidoResponseDTO> model = EntityModel.of(dto);

        // Links estáticos padrão
        model.add(linkTo(methodOn(PedidoController.class)
                .buscarPorId(dto.idPedido()))
                .withSelfRel());

        model.add(linkTo(methodOn(PedidoController.class)
                .buscarTodos())
                .withRel("lista-pedidos"));

        // HATEOAS Avançado: Links Condicionais por Estado do Pedido
        if ("ABERTO".equalsIgnoreCase(dto.status())) {
            // Se o pedido está aberto, permite que o fluxo siga para a criação de um pagamento
            model.add(linkTo(methodOn(PagamentoController.class)
                    .salvar(null)) // Aponta para o endpoint de criação de pagamentos
                    .withRel("efetuar-pagamento"));

            // Permite cancelar/deletar o pedido enquanto estiver aberto
            model.add(linkTo(methodOn(PedidoController.class)
                    .deletar(dto.idPedido()))
                    .withRel("cancelar-pedido"));
        }

        return model;
    }
}