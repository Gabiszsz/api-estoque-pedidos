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

import com.estoque.pedidos.dto.request.PedidoRequestDTO;
import com.estoque.pedidos.dto.response.PedidoResponseDTO;
import com.estoque.pedidos.service.PedidoService;
import com.estoque.pedidos.assembler.PedidoModelAssembler;
import com.estoque.pedidos.exception.ExceptionResponse;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Gerenciamento de ordens de vendas e controle de status")
public class PedidoController {

    private final PedidoService service;
    private final PedidoModelAssembler assembler;

    public PedidoController(PedidoService service, PedidoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar pedidos")
    public List<EntityModel<PedidoResponseDTO>> buscarTodos() {
        return service.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido", responses = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public EntityModel<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Abrir um novo pedido", description = "Abre um pedido vinculando a um cliente. O status inicial será 'ABERTO'.")
    public EntityModel<PedidoResponseDTO> salvar(@Valid @RequestBody PedidoRequestDTO requestDTO) {
        return assembler.toModel(service.save(requestDTO));
    }

    // AÇÃO ESPECÍFICA
    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido aberto", description = "Muda o status do pedido para 'CANCELADO'. Apenas pedidos não pagos podem ser cancelados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Regra de negócio: Pedido já está pago ou cancelado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<PedidoResponseDTO> cancelarPedido(@PathVariable Long id) {
        return assembler.toModel(service.cancelar(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente do pedido")
    public EntityModel<PedidoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO requestDTO) {
        return assembler.toModel(service.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar pedido")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}