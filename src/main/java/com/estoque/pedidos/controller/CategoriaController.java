package com.estoque.pedidos.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.estoque.pedidos.dto.request.CategoriaRequestDTO;
import com.estoque.pedidos.dto.response.CategoriaResponseDTO;
import com.estoque.pedidos.service.CategoriaService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoriaResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CategoriaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponseDTO salvar(@Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        return service.save(categoriaDTO);
    }

    @PutMapping("/{id}")
    public CategoriaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        return service.update(id, categoriaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}