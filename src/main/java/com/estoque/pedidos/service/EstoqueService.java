package com.estoque.pedidos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.estoque.pedidos.exception.RegraNegocioException;
import com.estoque.pedidos.model.Fornecedor;
import com.estoque.pedidos.repository.FornecedorRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.estoque.pedidos.model.Estoque;
import com.estoque.pedidos.model.Produto;
import com.estoque.pedidos.dto.request.EstoqueRequestDTO;
import com.estoque.pedidos.dto.response.EstoqueResponseDTO;
import com.estoque.pedidos.repository.EstoqueRepository;
import com.estoque.pedidos.repository.ProdutoRepository;
import com.estoque.pedidos.mapper.EstoqueMapper;
import com.estoque.pedidos.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

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
    @Caching(evict = {
            @CacheEvict(value = "listaProdutos", allEntries = true),
            @CacheEvict(value = "produtoUnico", allEntries = true)
    })
    public EstoqueResponseDTO save(EstoqueRequestDTO requestDTO) {

        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + requestDTO.produtoId()));


        Fornecedor fornecedor = fornecedorRepository.findById(requestDTO.fornecedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com o ID: " + requestDTO.fornecedorId()));


        Estoque estoque = mapper.toEntity(requestDTO);
        estoque.setProduto(produto);
        estoque.setFornecedor(fornecedor);
        estoque.setDataEntrada(LocalDate.now()); // O sistema registra o dia da chegada

        produto.adicionarEstoque(requestDTO.quantidadeAtual());
        produtoRepository.save(produto);


        Estoque estoqueSalvo = repository.save(estoque);
        return mapper.toResponseDTO(estoqueSalvo);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "listaProdutos", allEntries = true),
            @CacheEvict(value = "produtoUnico", allEntries = true)
    })
    public EstoqueResponseDTO update(Long id, EstoqueRequestDTO requestDTO) {

        Estoque estoqueAntigo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registo de stock não encontrado com o ID: " + id));

        Integer quantidadeAntiga = estoqueAntigo.getQuantidadeAtual();

        mapper.updateEntityFromDTO(requestDTO, estoqueAntigo);

        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + requestDTO.produtoId()));
        estoqueAntigo.setProduto(produto);

        Estoque estoqueSalvo = repository.save(estoqueAntigo);

        int diferenca = estoqueSalvo.getQuantidadeAtual() - quantidadeAntiga;

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + diferenca);
        produtoRepository.save(produto);

        return mapper.toResponseDTO(estoqueSalvo);
    }


    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "listaProdutos", allEntries = true),
            @CacheEvict(value = "produtoUnico", allEntries = true)
    })
    public void delete(Long id) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registo de stock não encontrado com o ID: " + id));

        Produto produto = estoque.getProduto();

        int novoEstoqueGlobal = produto.getQuantidadeEstoque() - estoque.getQuantidadeAtual();

        if (novoEstoqueGlobal < 0) {
            throw new RegraNegocioException("Não é possível apagar este lote pois o stock global do produto ficaria negativo.");
        }

        produto.setQuantidadeEstoque(novoEstoqueGlobal);
        produtoRepository.save(produto);

        repository.delete(estoque);
    }
}