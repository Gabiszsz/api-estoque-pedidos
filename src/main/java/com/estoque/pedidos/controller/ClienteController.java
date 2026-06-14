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

import com.estoque.pedidos.dto.request.ClienteRequestDTO;
import com.estoque.pedidos.dto.response.ClienteResponseDTO;
import com.estoque.pedidos.service.ClienteService;
import com.estoque.pedidos.assembler.ClienteModelAssembler;
import com.estoque.pedidos.exception.ExceptionResponse;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Endpoints para o gerenciamento de clientes e validação de CPFs")
public class ClienteController {

    private final ClienteService service;
    private final ClienteModelAssembler assembler;

    public ClienteController(ClienteService service, ClienteModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de clientes com links de navegação",
            responses = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") }
    )
    public List<EntityModel<ClienteResponseDTO>> buscarTodos() {
        return service.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico baseado no seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar novo cliente", description = "Cria um cliente validando o formato e unicidade do CPF",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação (ex: CPF inválido ou duplicado)",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<ClienteResponseDTO> salvar(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
        return assembler.toModel(service.save(clienteDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<ClienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteDTO) {
        return assembler.toModel(service.update(id, clienteDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Conflito: Cliente possui pedidos vinculados",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}