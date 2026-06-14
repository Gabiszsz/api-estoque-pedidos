package com.estoque.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.model.vo.Preco; // Certifique-se de importar o Preco

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    // Agora apontamos direto para o objeto 'preco', não mais para 'preco.valor'
    @Mapping(source = "precoVenda", target = "preco")
    Produto toEntity(ProdutoRequestDTO dto);

    // O mapeamento inverso funciona bem porque a leitura (.valor()) existe nos records
    @Mapping(source = "preco.valor", target = "preco")
    @Mapping(source = "quantidadeEstoque", target = "quantidade")
    ProdutoResponseDTO toResponseDTO(Produto produto);

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