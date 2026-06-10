package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toEntity(ProdutoRequestDTO dto);

    @Mapping(source = "precoVenda", target = "preco")
    @Mapping(source = "quantidadeEstoque", target = "quantidade")
    ProdutoResponseDTO toResponseDTO(Produto produto);

    void updateEntityFromDTO(ProdutoRequestDTO dto, @MappingTarget Produto produto);
}