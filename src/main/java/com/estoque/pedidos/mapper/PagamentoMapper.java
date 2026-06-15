package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Pagamento;
import com.estoque.pedidos.dto.request.PagamentoRequestDTO;
import com.estoque.pedidos.dto.response.PagamentoResponseDTO;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {


    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "statusPagamento", ignore = true)
    Pagamento toEntity(PagamentoRequestDTO dto);


    @Mapping(source = "pedido.idPedido", target = "pedidoId")
    PagamentoResponseDTO toResponseDTO(Pagamento pagamento);


    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "statusPagamento", ignore = true)
    void updateEntityFromDTO(PagamentoRequestDTO dto, @MappingTarget Pagamento pagamento);
}