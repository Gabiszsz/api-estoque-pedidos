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
    ItemPedido toEntity(ItemPedidoRequestDTO dto);

    @Mapping(source = "pedido.idPedido", target = "pedidoId")
    @Mapping(source = "produto.id", target = "produtoId")
    ItemPedidoResponseDTO toResponseDTO(ItemPedido itemPedido);

    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "produto", ignore = true)
    void updateEntityFromDTO(ItemPedidoRequestDTO dto, @MappingTarget ItemPedido itemPedido);
}
//Nota explicativa: Semelhante ao pagamento, o ItemPedidoResponseDTO retorna apenas IDs planos (pedidoId e produtoId).
// Mapeamos os caminhos dos objetos internos da entidade (pedido.idPedido e produto.id) para alimentar estes campos no DTO.