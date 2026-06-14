package com.estoque.pedidos.controller;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.estoque.pedidos.dto.request.ClienteRequestDTO;
import com.estoque.pedidos.dto.response.ClienteResponseDTO;
import com.estoque.pedidos.service.ClienteService;
import com.estoque.pedidos.assembler.ClienteModelAssembler;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;
    private final ClienteModelAssembler assembler;

    public ClienteController(ClienteService service, ClienteModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public List<EntityModel<ClienteResponseDTO>> buscarTodos() {
        return service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EntityModel<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<ClienteResponseDTO> salvar(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
        return assembler.toModel(service.save(clienteDTO));
    }

    @PutMapping("/{id}")
    public EntityModel<ClienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteDTO) {
        return assembler.toModel(service.update(id, clienteDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}