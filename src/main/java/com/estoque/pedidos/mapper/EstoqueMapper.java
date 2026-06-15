package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Estoque;
import com.estoque.pedidos.dto.request.EstoqueRequestDTO;
import com.estoque.pedidos.dto.response.EstoqueResponseDTO;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface EstoqueMapper {

    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "fornecedor", ignore = true) // <-- Adicione isso
    @Mapping(target = "dataEntrada", ignore = true)
    Estoque toEntity(EstoqueRequestDTO dto);

    EstoqueResponseDTO toResponseDTO(Estoque estoque);

    @Mapping(target = "produto", ignore = true)
    void updateEntityFromDTO(EstoqueRequestDTO dto, @MappingTarget Estoque estoque);
}
//aninha os detalhes de um ProdutoResponseDTO, adicionamos uses = {ProdutoMapper.class}.
// Ignoramos a entidade produto na criação/atualização porque ela é injetada manualmente pelo ID no Service.