package com.estoque.pedidos.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.estoque.pedidos.controller.FornecedorController;
import com.estoque.pedidos.dto.response.FornecedorResponseDTO;

@Component
public class FornecedorModelAssembler implements RepresentationModelAssembler<FornecedorResponseDTO, EntityModel<FornecedorResponseDTO>> {

    @Override
    public EntityModel<FornecedorResponseDTO> toModel(FornecedorResponseDTO dto) {
        EntityModel<FornecedorResponseDTO> model = EntityModel.of(dto);

        // Self link
        model.add(linkTo(methodOn(FornecedorController.class)
                .buscarPorId(dto.id()))
                .withSelfRel());

        // Link para a listagem geral
        model.add(linkTo(methodOn(FornecedorController.class)
                .buscarTodos())
                .withRel("lista-fornecedores"));

        return model;
    }
}