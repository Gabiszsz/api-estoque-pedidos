package com.estoque.pedidos.controller;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.estoque.pedidos.dto.request.PagamentoRequestDTO;
import com.estoque.pedidos.dto.response.PagamentoResponseDTO;
import com.estoque.pedidos.service.PagamentoService;
import com.estoque.pedidos.assembler.PagamentoModelAssembler;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;
    private final PagamentoModelAssembler assembler; // Injeção do Assembler do Pagamento

    public PagamentoController(PagamentoService service, PagamentoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public List<EntityModel<PagamentoResponseDTO>> buscarTodos() {
        return service.findAll().stream()
                .map(assembler::toModel) // Transforma a lista de pagamentos em modelos navegáveis
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EntityModel<PagamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<PagamentoResponseDTO> salvar(@Valid @RequestBody PagamentoRequestDTO pagamentoDTO) {
        return assembler.toModel(service.save(pagamentoDTO));
    }

    @PutMapping("/{id}")
    public EntityModel<PagamentoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PagamentoRequestDTO pagamentoDTO) {
        return assembler.toModel(service.update(id, pagamentoDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}