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

import com.estoque.pedidos.dto.request.CategoriaRequestDTO;
import com.estoque.pedidos.dto.response.CategoriaResponseDTO;
import com.estoque.pedidos.service.CategoriaService;
import com.estoque.pedidos.exception.ExceptionResponse;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Endpoints para gerenciamento das categorias de produtos")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas as categorias",
            responses = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") }
    )
    public List<CategoriaResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public CategoriaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar nova categoria",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public CategoriaResponseDTO salvar(@Valid @RequestBody CategoriaRequestDTO requestDTO) {
        return service.save(requestDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public CategoriaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO requestDTO) {
        return service.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar categoria",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Conflito: Categoria possui produtos vinculados", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}