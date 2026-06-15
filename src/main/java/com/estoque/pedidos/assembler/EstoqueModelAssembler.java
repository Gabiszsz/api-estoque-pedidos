package com.estoque.pedidos.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.estoque.pedidos.controller.EstoqueController;
import com.estoque.pedidos.controller.ProdutoController;
import com.estoque.pedidos.dto.response.EstoqueResponseDTO;

@Component
public class EstoqueModelAssembler implements RepresentationModelAssembler<EstoqueResponseDTO, EntityModel<EstoqueResponseDTO>> {

    @Override
    public EntityModel<EstoqueResponseDTO> toModel(EstoqueResponseDTO dto) {
        EntityModel<EstoqueResponseDTO> model = EntityModel.of(dto);

        // Self link
        model.add(linkTo(methodOn(EstoqueController.class)
                .buscarPorId(dto.id()))
                .withSelfRel());

        // Link para a listagem geral
        model.add(linkTo(methodOn(EstoqueController.class)
                .buscarTodos())
                .withRel("lista-estoques"));

        // Referência cruzada para o Produto vinculado
        if (dto.produto() != null) {
            model.add(linkTo(methodOn(ProdutoController.class)
                    .buscarPorId(dto.produto().id()))
                    .withRel("consultar-produto"));
        }

        return model;
    }
}