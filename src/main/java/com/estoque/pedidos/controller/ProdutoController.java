package com.estoque.pedidos.controller;

import java.math.BigDecimal;
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

import com.estoque.pedidos.dto.request.ProdutoRequestDTO;
import com.estoque.pedidos.dto.response.ProdutoResponseDTO;
import com.estoque.pedidos.service.ProdutoService;
import com.estoque.pedidos.assembler.ProdutoModelAssembler;
import com.estoque.pedidos.exception.ExceptionResponse;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Gerenciamento de catálogo e preços")
public class ProdutoController {

    private final ProdutoService service;
    private final ProdutoModelAssembler assembler;

    public ProdutoController(ProdutoService service, ProdutoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    public List<EntityModel<ProdutoResponseDTO>> buscarTodos() {
        return service.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar produto",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação (ex: SKU duplicado/Preço Negativo)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<ProdutoResponseDTO> salvar(@Valid @RequestBody ProdutoRequestDTO requestDTO) {
        return assembler.toModel(service.save(requestDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do produto")
    public EntityModel<ProdutoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO requestDTO) {
        return assembler.toModel(service.update(id, requestDTO));
    }

    @PatchMapping("/{id}/preco")
    @Operation(summary = "Ajustar Preço de Venda", description = "Atualiza pontualmente o preço de um produto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Preço atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Preço inválido (negativo)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public EntityModel<ProdutoResponseDTO> atualizarPreco(@PathVariable Long id, @RequestParam BigDecimal novoPreco) {
        return assembler.toModel(service.atualizarPreco(id, novoPreco));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar produto")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}