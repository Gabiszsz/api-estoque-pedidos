package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.model.vo.Preco;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface ProdutoMapper {

    @Mapping(target = "categoria", ignore = true)
    @Mapping(source = "precoVenda", target = "preco")
    Produto toEntity(ProdutoRequestDTO dto);

    @Mapping(source = "preco.valor", target = "preco")
    @Mapping(source = "quantidadeEstoque", target = "quantidade")
    ProdutoResponseDTO toResponseDTO(Produto produto);

    @Mapping(target = "categoria", ignore = true)
    @Mapping(source = "precoVenda", target = "preco")
    void updateEntityFromDTO(ProdutoRequestDTO dto, @MappingTarget Produto produto);

    // Método customizado: ensina o MapStruct a converter o Double em um Record 'Preco'
    default Preco mapPreco(Double precoVenda) {
        if (precoVenda == null) {
            return null;
        }
        return new Preco(precoVenda, "BRL");
    }
}