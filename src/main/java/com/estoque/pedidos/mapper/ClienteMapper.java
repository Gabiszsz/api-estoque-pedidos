package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.dto.request.ClienteRequestDTO;
import com.estoque.pedidos.dto.response.ClienteResponseDTO;
import com.estoque.pedidos.model.vo.Cpf;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(source = "cpf", target = "cpf")
    Cliente toEntity(ClienteRequestDTO dto);

    @Mapping(source = "cpf.valor", target = "cpf")
    ClienteResponseDTO toResponseDTO(Cliente cliente);

    @Mapping(source = "cpf", target = "cpf")
    void updateEntityFromDTO(ClienteRequestDTO dto, @MappingTarget Cliente cliente);

    // Método customizado para o MapStruct instanciar o Record imutável do VO Cpf
    default Cpf mapCpf(String valor) {
        return valor == null ? null : new Cpf(valor);
    }
}