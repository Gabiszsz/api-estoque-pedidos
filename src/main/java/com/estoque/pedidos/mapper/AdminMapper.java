package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Admin;
import com.estoque.pedidos.dto.request.AdminRequestDTO;
import com.estoque.pedidos.dto.response.AdminResponseDTO;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin toEntity(AdminRequestDTO dto);
    AdminResponseDTO toResponseDTO(Admin admin);
    void updateEntityFromDTO(AdminRequestDTO dto, @MappingTarget Admin admin);
}