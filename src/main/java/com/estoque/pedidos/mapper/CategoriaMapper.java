package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Categoria;
import com.estoque.pedidos.dto.request.CategoriaRequestDTO;
import com.estoque.pedidos.dto.response.CategoriaResponseDTO;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    Categoria toEntity(CategoriaRequestDTO dto);
    CategoriaResponseDTO toResponseDTO(Categoria categoria);
    void updateEntityFromDTO(CategoriaRequestDTO dto, @MappingTarget Categoria categoria);
}