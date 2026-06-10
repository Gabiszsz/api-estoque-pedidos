package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Fornecedor;
import com.estoque.pedidos.dto.request.FornecedorRequestDTO;
import com.estoque.pedidos.dto.response.FornecedorResponseDTO;

@Mapper(componentModel = "spring")
public interface FornecedorMapper {
    Fornecedor toEntity(FornecedorRequestDTO dto);
    FornecedorResponseDTO toResponseDTO(Fornecedor fornecedor);
    void updateEntityFromDTO(FornecedorRequestDTO dto, @MappingTarget Fornecedor fornecedor);
}