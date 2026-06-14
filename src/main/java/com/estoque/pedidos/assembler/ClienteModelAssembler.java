package com.estoque.pedidos.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.estoque.pedidos.controller.ClienteController;
import com.estoque.pedidos.dto.response.ClienteResponseDTO;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteResponseDTO, EntityModel<ClienteResponseDTO>> {

    @Override
    public EntityModel<ClienteResponseDTO> toModel(ClienteResponseDTO dto) {
        EntityModel<ClienteResponseDTO> model = EntityModel.of(dto);

        // Self Link: Link para o próprio cliente
        model.add(linkTo(methodOn(ClienteController.class)
                .buscarPorId(dto.id()))
                .withSelfRel());

        // Link para a listagem de todos os clientes
        model.add(linkTo(methodOn(ClienteController.class)
                .buscarTodos())
                .withRel("lista-clientes"));

        return model;
    }
}