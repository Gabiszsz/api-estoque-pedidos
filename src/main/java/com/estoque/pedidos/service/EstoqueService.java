package com.estoque.pedidos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.estoque.pedidos.model.Fornecedor;
import com.estoque.pedidos.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Estoque;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.EstoqueRequestDTO;
import com.estoque.pedidos.dto.response.EstoqueResponseDTO;
import com.estoque.pedidos.repository.EstoqueRepository;
import com.estoque.pedidos.repository.ProdutoRepository;
import com.estoque.pedidos.mapper.EstoqueMapper;
import com.estoque.pedidos.exception.ResourceNotFoundException;

@Service
public class EstoqueService {

    private final EstoqueRepository repository;
    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;
    private final EstoqueMapper mapper;

    public EstoqueService(EstoqueRepository repository, ProdutoRepository produtoRepository, FornecedorRepository fornecedorRepository, EstoqueMapper mapper) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.mapper = mapper;
    }

    public List<EstoqueResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EstoqueResponseDTO findById(Long id) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado com o ID: " + id));
        return mapper.toResponseDTO(estoque);
    }

    public EstoqueResponseDTO save(EstoqueRequestDTO requestDTO) {
        // 1. Valida se o produto existe
        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + requestDTO.produtoId()));

        // 2. Valida se o fornecedor existe
        Fornecedor fornecedor = fornecedorRepository.findById(requestDTO.fornecedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com o ID: " + requestDTO.fornecedorId()));

        // 3. Monta a entidade
        Estoque estoque = mapper.toEntity(requestDTO);
        estoque.setProduto(produto);
        estoque.setFornecedor(fornecedor);
        estoque.setDataEntrada(LocalDate.now()); // O sistema registra o dia da chegada

        // 4. A REGRA DE OURO (Efeito Colateral):
        // Se chegaram 50 mouses neste lote (Estoque), o total global de mouses no Produto deve subir 50!
        produto.adicionarEstoque(requestDTO.quantidadeAtual());
        produtoRepository.save(produto);

        // 5. Salva o registro de entrada
        Estoque estoqueSalvo = repository.save(estoque);
        return mapper.toResponseDTO(estoqueSalvo);
    }

    public EstoqueResponseDTO update(Long id, EstoqueRequestDTO requestDTO) {
        Estoque estoqueExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado com o ID: " + id));

        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + requestDTO.produtoId()));

        mapper.updateEntityFromDTO(requestDTO, estoqueExistente);
        estoqueExistente.setProduto(produto);

        Estoque estoqueAtualizado = repository.save(estoqueExistente);
        return mapper.toResponseDTO(estoqueAtualizado);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Estoque não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}