package com.estoque.pedidos.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.estoque.pedidos.controller.CategoriaController;
import com.estoque.pedidos.dto.response.CategoriaResponseDTO;

@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<CategoriaResponseDTO, EntityModel<CategoriaResponseDTO>> {

    @Override
    public EntityModel<CategoriaResponseDTO> toModel(CategoriaResponseDTO dto) {
        EntityModel<CategoriaResponseDTO> model = EntityModel.of(dto);

        // Self link
        model.add(linkTo(methodOn(CategoriaController.class)
                .buscarPorId(dto.id()))
                .withSelfRel());

        // Link para a listagem geral
        model.add(linkTo(methodOn(CategoriaController.class)
                .buscarTodos())
                .withRel("lista-categorias"));

        return model;
    }
}