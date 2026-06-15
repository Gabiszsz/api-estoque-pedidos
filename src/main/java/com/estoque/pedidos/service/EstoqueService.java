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

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "listaProdutos", allEntries = true),
            @CacheEvict(value = "produtoUnico", allEntries = true)
    })
    public EstoqueResponseDTO update(Long id, EstoqueRequestDTO requestDTO) {
        // 1. Busca o registo de stock antigo
        Estoque estoqueAntigo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registo de stock não encontrado com o ID: " + id));

        // 2. Guarda a quantidade que estava registada antes
        Integer quantidadeAntiga = estoqueAntigo.getQuantidadeAtual();

        // 3. Atualiza os dados do stock com o DTO novo
        mapper.updateEntityFromDTO(requestDTO, estoqueAntigo);

        // CORREÇÃO: Como o DTO é um 'record', chamamos diretamente o nome do atributo .produtoId()
        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + requestDTO.produtoId()));
        estoqueAntigo.setProduto(produto);

        // 4. Salva o stock atualizado
        Estoque estoqueSalvo = repository.save(estoqueAntigo);

        // 5. MATEMÁTICA DE COMPENSAÇÃO NO PRODUTO GLOBAL:
        int diferenca = estoqueSalvo.getQuantidadeAtual() - quantidadeAntiga;

        // Atualiza o stock global do produto somando a diferença (pode ser positiva ou negativa)
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
        // 1. Busca o stock para saber qual produto e qual quantidade serão afetados
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registo de stock não encontrado com o ID: " + id));

        Produto produto = estoque.getProduto();

        // 2. Subtrai a quantidade do lote que está a ser apagado do stock global do produto (CORRIGIDO: getQuantidadeAtual)
        int novoEstoqueGlobal = produto.getQuantidadeEstoque() - estoque.getQuantidadeAtual();

        // Opcional: Uma validação de segurança para não deixar o stock global ficar negativo
        if (novoEstoqueGlobal < 0) {
            throw new RegraNegocioException("Não é possível apagar este lote pois o stock global do produto ficaria negativo.");
        }

        produto.setQuantidadeEstoque(novoEstoqueGlobal);
        produtoRepository.save(produto);

        // 3. Apaga o registo do banco de dados
        repository.delete(estoque);
    }
}