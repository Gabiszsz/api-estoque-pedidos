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

import com.estoque.pedidos.dto.request.FornecedorRequestDTO;
import com.estoque.pedidos.dto.response.FornecedorResponseDTO;
import com.estoque.pedidos.service.FornecedorService;
import com.estoque.pedidos.assembler.FornecedorModelAssembler;
import com.estoque.pedidos.exception.ExceptionResponse;

@RestController
@RequestMapping("/fornecedores")
@Tag(name = "Fornecedores", description = "Endpoints para gerenciamento de fornecedores e validação de CNPJ")
public class FornecedorController {

    private final FornecedorService service;
    private final FornecedorModelAssembler assembler;

    public FornecedorController(FornecedorService service, FornecedorModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar todos os fornecedores")
    public List<EntityModel<FornecedorResponseDTO>> buscarTodos() {
        return service.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar fornecedor por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fornecedor encontrado"),
                    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<FornecedorResponseDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar fornecedor", description = "Valida o formato exato de 14 dígitos do CNPJ (Value Object)",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Fornecedor cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação (CNPJ inválido)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<FornecedorResponseDTO> salvar(@Valid @RequestBody FornecedorRequestDTO requestDTO) {
        return assembler.toModel(service.save(requestDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fornecedor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<FornecedorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FornecedorRequestDTO requestDTO) {
        return assembler.toModel(service.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar fornecedor",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Fornecedor deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}