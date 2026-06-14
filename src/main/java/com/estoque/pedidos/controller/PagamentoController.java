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

import com.estoque.pedidos.dto.request.PagamentoRequestDTO;
import com.estoque.pedidos.dto.response.PagamentoResponseDTO;
import com.estoque.pedidos.service.PagamentoService;
import com.estoque.pedidos.assembler.PagamentoModelAssembler;
import com.estoque.pedidos.exception.ExceptionResponse;

@RestController
@RequestMapping("/pagamentos")
@Tag(name = "Pagamentos", description = "Endpoints para registo e gestão de transações financeiras dos pedidos")
public class PagamentoController {

    private final PagamentoService service;
    private final PagamentoModelAssembler assembler;

    public PagamentoController(PagamentoService service, PagamentoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar todos os pagamentos",
            responses = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") }
    )
    public List<EntityModel<PagamentoResponseDTO>> buscarTodos() {
        return service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<PagamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Efetuar pagamento", description = "Regista um novo pagamento. Só é possível pagar pedidos com status 'ABERTO'.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pagamento registado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação (ex: valor divergente ou pedido já pago)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<PagamentoResponseDTO> salvar(@Valid @RequestBody PagamentoRequestDTO pagamentoDTO) {
        return assembler.toModel(service.save(pagamentoDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pagamento",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<PagamentoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PagamentoRequestDTO pagamentoDTO) {
        return assembler.toModel(service.update(id, pagamentoDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar pagamento",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pagamento deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}