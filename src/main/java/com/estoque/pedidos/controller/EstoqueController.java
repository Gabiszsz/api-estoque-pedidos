package com.estoque.pedidos.controller;

import java.util.List;
import jakarta.validation.Valid;

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
import com.estoque.pedidos.exception.ExceptionResponse;

@RestController
@RequestMapping("/estoques")
@Tag(name = "Estoque", description = "Endpoints para consulta avançada de lotes e posições de stock")
public class EstoqueController {

    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas as posições de stock")
    public List<EstoqueResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar stock por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stock encontrado"),
                    @ApiResponse(responseCode = "404", description = "Stock não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EstoqueResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registar nova entrada de stock",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Entrada registada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EstoqueResponseDTO salvar(@Valid @RequestBody EstoqueRequestDTO requestDTO) {
        return service.save(requestDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registo de stock",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stock atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Stock não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EstoqueResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody EstoqueRequestDTO requestDTO) {
        return service.update(id, requestDTO);
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