package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Fornecedor;
import com.estoque.pedidos.dto.request.FornecedorRequestDTO;
import com.estoque.pedidos.dto.response.FornecedorResponseDTO;
import com.estoque.pedidos.model.vo.Cnpj;

@Mapper(componentModel = "spring")
public interface FornecedorMapper {

    @Mapping(source = "cnpj", target = "cnpj")
    Fornecedor toEntity(FornecedorRequestDTO dto);

    @Mapping(source = "cnpj.valor", target = "cnpj")
    FornecedorResponseDTO toResponseDTO(Fornecedor fornecedor);

    @Mapping(source = "cnpj", target = "cnpj")
    void updateEntityFromDTO(FornecedorRequestDTO dto, @MappingTarget Fornecedor fornecedor);

    // Método customizado para o MapStruct instanciar o Record imutável do VO Cnpj
    default Cnpj mapCnpj(String valor) {
        return valor == null ? null : new Cnpj(valor);
    }
}