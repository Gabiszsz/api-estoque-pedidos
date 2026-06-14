package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.ItemPedido;
import com.estoque.pedidos.dto.request.ItemPedidoRequestDTO;
import com.estoque.pedidos.dto.response.ItemPedidoResponseDTO;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "precoUnitario", ignore = true) // Ignora o preço ao criar
    ItemPedido toEntity(ItemPedidoRequestDTO dto);

    @Mapping(source = "pedido.idPedido", target = "pedidoId")
    @Mapping(source = "produto.id", target = "produtoId")
    ItemPedidoResponseDTO toResponseDTO(ItemPedido itemPedido);

    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "precoUnitario", ignore = true) // Ignora o preço ao atualizar
    void updateEntityFromDTO(ItemPedidoRequestDTO dto, @MappingTarget ItemPedido itemPedido);
}