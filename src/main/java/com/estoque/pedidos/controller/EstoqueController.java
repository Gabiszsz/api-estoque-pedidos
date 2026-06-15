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

import com.estoque.pedidos.dto.request.EstoqueRequestDTO;
import com.estoque.pedidos.dto.response.EstoqueResponseDTO;
import com.estoque.pedidos.service.EstoqueService;
import com.estoque.pedidos.assembler.EstoqueModelAssembler;
import com.estoque.pedidos.exception.ExceptionResponse;

@RestController
@RequestMapping("/estoques")
@Tag(name = "Estoque", description = "Endpoints para consulta avançada de lotes e posições de stock")
public class EstoqueController {

    private final EstoqueService service;
    private final EstoqueModelAssembler assembler;

    public EstoqueController(EstoqueService service, EstoqueModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar todas as posições de stock")
    public List<EntityModel<EstoqueResponseDTO>> buscarTodos() {
        return service.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar stock por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stock encontrado"),
                    @ApiResponse(responseCode = "404", description = "Stock não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<EstoqueResponseDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registar nova entrada de stock",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Entrada registada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<EstoqueResponseDTO> salvar(@Valid @RequestBody EstoqueRequestDTO requestDTO) {
        return assembler.toModel(service.save(requestDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registo de stock",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stock jackpot atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Stock não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<EstoqueResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EstoqueRequestDTO requestDTO) {
        return assembler.toModel(service.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar registo de stock",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Registo eliminado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Stock não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}