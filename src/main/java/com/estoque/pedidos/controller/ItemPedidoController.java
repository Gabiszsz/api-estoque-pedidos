package com.estoque.pedidos.controller;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.estoque.pedidos.dto.request.ItemPedidoRequestDTO;
import com.estoque.pedidos.dto.response.ItemPedidoResponseDTO;
import com.estoque.pedidos.service.ItemPedidoService;
import com.estoque.pedidos.assembler.ItemPedidoModelAssembler;
import com.estoque.pedidos.exception.ExceptionResponse;

@RestController
@RequestMapping("/itens-pedido")
@Tag(name = "Itens do Pedido", description = "Endpoints para gerir os produtos vinculados a um pedido")
public class ItemPedidoController {

    private final ItemPedidoService service;
    private final ItemPedidoModelAssembler assembler;

    public ItemPedidoController(ItemPedidoService service, ItemPedidoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar todos os itens de pedidos")
    public List<EntityModel<ItemPedidoResponseDTO>> buscarTodos() {
        return service.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item encontrado"),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<ItemPedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar item ao pedido", description = "Adiciona um produto a um pedido existente, reservando o stock automaticamente.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Item adicionado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Stock insuficiente ou dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Pedido ou Produto não encontrados", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<ItemPedidoResponseDTO> salvar(@Valid @RequestBody ItemPedidoRequestDTO requestDTO) {
        return assembler.toModel(service.save(requestDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item do pedido", description = "Altera as quantidades de um item, reajustando o stock dinamicamente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Stock insuficiente para o reajuste", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<ItemPedidoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ItemPedidoRequestDTO requestDTO) {
        return assembler.toModel(service.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover item do pedido", description = "Remove o item e devolve a quantidade reservada ao stock do produto.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Item removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}