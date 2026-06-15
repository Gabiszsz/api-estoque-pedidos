package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Pedido;
import com.estoque.pedidos.dto.request.PedidoRequestDTO;
import com.estoque.pedidos.dto.response.PedidoResponseDTO;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ItemPedidoMapper.class})
public interface PedidoMapper {

    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "itens", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    @Mapping(target = "status", ignore = true)     
    Pedido toEntity(PedidoRequestDTO dto);

    PedidoResponseDTO toResponseDTO(Pedido pedido);

    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "itens", ignore = true)
    void updateEntityFromDTO(PedidoRequestDTO dto, @MappingTarget Pedido pedido);
}