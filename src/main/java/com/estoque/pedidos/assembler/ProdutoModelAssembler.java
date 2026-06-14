package com.estoque.pedidos.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.estoque.pedidos.controller.ProdutoController;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;

@Component
public class ProdutoModelAssembler implements RepresentationModelAssembler<ProdutoResponseDTO, EntityModel<ProdutoResponseDTO>> {

    @Override
    public EntityModel<ProdutoResponseDTO> toModel(ProdutoResponseDTO dto) {
        // Envolve o DTO num "Envelope" HATEOAS
        EntityModel<ProdutoResponseDTO> model = EntityModel.of(dto);

        // Adiciona um Link para si mesmo (Self Link)
        model.add(linkTo(methodOn(ProdutoController.class)
                .buscarPorId(dto.id()))
                .withSelfRel());

        // Adiciona um Link de volta para a lista completa de produtos
        model.add(linkTo(methodOn(ProdutoController.class)
                .buscarTodos())
                .withRel("lista-produtos"));
        // Exemplo de regra condicional (HATEOAS avançado):
        // Só adicionamos o link de "excluir" se o produto não tiver estoque
        if (dto.quantidade() == 0) {
            model.add(linkTo(methodOn(ProdutoController.class)
                    .deletar(dto.id()))
                    .withRel("excluir"));
        }

        return model;
    }
}