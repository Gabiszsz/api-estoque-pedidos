package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    // Envia o "precoVenda" do RequestDTO para dentro do VO "preco.valor"
    @Mapping(source = "precoVenda", target = "preco.valor")
    @Mapping(target = "preco.moeda", constant = "BRL")
    Produto toEntity(ProdutoRequestDTO dto);

    // Pega o VO "preco.valor" da Entidade e joga para o "preco" do ResponseDTO
    @Mapping(source = "preco.valor", target = "preco")
    @Mapping(source = "quantidadeEstoque", target = "quantidade")
    ProdutoResponseDTO toResponseDTO(Produto produto);

    @Mapping(source = "precoVenda", target = "preco.valor")
    @Mapping(target = "preco.moeda", constant = "BRL")
    void updateEntityFromDTO(ProdutoRequestDTO dto, @MappingTarget Produto produto);
}