package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.dto.request.ClienteRequestDTO;
import com.estoque.pedidos.dto.response.ClienteResponseDTO;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente toEntity(ClienteRequestDTO dto);
    ClienteResponseDTO toResponseDTO(Cliente cliente);
    void updateEntityFromDTO(ClienteRequestDTO dto, @MappingTarget Cliente cliente);
}