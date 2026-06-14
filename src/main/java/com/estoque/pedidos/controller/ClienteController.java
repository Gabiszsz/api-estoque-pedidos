package com.estoque.pedidos.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.estoque.pedidos.dto.request.ClienteRequestDTO;
import com.estoque.pedidos.dto.response.ClienteResponseDTO;
import com.estoque.pedidos.service.ClienteService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClienteResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO salvar(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
        return service.save(clienteDTO);
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteDTO) {
        return service.update(id, clienteDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}