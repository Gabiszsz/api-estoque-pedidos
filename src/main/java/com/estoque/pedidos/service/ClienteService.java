package com.estoque.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Cliente;
import com.estoque.pedidos.dto.request.ClienteRequestDTO;
import com.estoque.pedidos.dto.response.ClienteResponseDTO;
import com.estoque.pedidos.repository.ClienteRepository;
import com.estoque.pedidos.mapper.ClienteMapper; // Import adicionado

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper; // Declarado como final

    public ClienteService(ClienteRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ClienteResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO) // Usa MapStruct
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
        return mapper.toResponseDTO(cliente);
    }

    public ClienteResponseDTO save(ClienteRequestDTO requestDTO) {
        Cliente cliente = mapper.toEntity(requestDTO);
        Cliente clienteSalvo = repository.save(cliente);
        return mapper.toResponseDTO(clienteSalvo);
    }

    public ClienteResponseDTO update(Long id, ClienteRequestDTO requestDTO) {
        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));

        // Atualização automática via MapStruct
        mapper.updateEntityFromDTO(requestDTO, clienteExistente);

        Cliente clienteAtualizado = repository.save(clienteExistente);
        return mapper.toResponseDTO(clienteAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}