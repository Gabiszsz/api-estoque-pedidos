# API de Gestão de Estoque e Pedidos

## Integrantes do Grupo

* Gabriella Alves do Nascimento
* Letícia Inácio Pereira Vaz
* Marcus Augusto Correa Bernardo

---

## Descrição do Problema

Empresas que realizam vendas de produtos precisam gerenciar clientes, fornecedores, estoque, pedidos e pagamentos de forma eficiente. O controle manual dessas informações pode gerar inconsistências, perda de dados e dificuldades no acompanhamento das operações.

Este projeto propõe o desenvolvimento de uma API REST para gerenciamento de estoque e pedidos, permitindo o cadastro e controle de produtos, clientes, fornecedores, pedidos, itens de pedido e pagamentos.

---

## Objetivo

Desenvolver uma API REST utilizando Java e Spring Boot aplicando conceitos modernos de desenvolvimento backend, persistência de dados e boas práticas de desenvolvimento web.

---

## Requisitos Funcionais / Casos de Uso

### Produtos

* Cadastrar produto
* Consultar produto
* Atualizar produto
* Remover produto
* Controlar estoque
* Validar SKU único

### Clientes

* Cadastrar cliente
* Consultar cliente
* Atualizar cliente
* Remover cliente

### Fornecedores

* Cadastrar fornecedor
* Consultar fornecedor
* Atualizar fornecedor
* Remover fornecedor

### Pedidos

* Criar pedido
* Associar cliente ao pedido
* Calcular valor total automaticamente

### Itens do Pedido

* Adicionar produtos ao pedido
* Atualizar itens
* Remover itens
* Baixar estoque automaticamente

### Pagamentos

* Registrar pagamento
* Validar valor pago
* Atualizar status do pedido

---

## Regras de Negócio Implementadas

### Produto

* Não permite SKU duplicado.
* Não permite preço negativo.
* Não permite estoque negativo.
* Nome do produto é obrigatório.

### ItemPedido

* Quantidade deve ser maior que zero.
* Estoque é reduzido automaticamente.
* Valor total do pedido é recalculado automaticamente.

### Pedido

* Valor total é calculado automaticamente pelos itens.

### Pagamento

* Apenas pedidos com status ABERTO podem ser pagos.
* O valor pago deve ser igual ao valor total do pedido.
* Após o pagamento, o pedido recebe status PAGO.

### Fornecedor

* Não é permitido cadastrar fornecedor sem contato.

---

## Arquitetura Utilizada

O sistema foi desenvolvido utilizando arquitetura em camadas:

* Assembler
* Config
* Controller
* DTO
* Exception
* Mapper
* Model
* Repository
* Service
* Test

---

## Banco de Dados

Banco utilizado:

* MySQL

Tecnologias utilizadas:

* Spring Data JPA
* Hibernate

Relacionamentos implementados:

* Cliente 1:N Pedido
* Pedido 1:N ItemPedido
* Produto 1:N ItemPedido
* Pedido 1:1 Pagamento

---

## Diagrama de Classes

Disponível no documento 'DIAGRAMAS'

---

## DER (Diagrama Entidade Relacionamento)

Disponível no documento 'DIAGRAMAS'

Entidades principais:

* Cliente
* Produto
* Fornecedor
* Pedido
* ItemPedido
* Pagamento

---

## Tecnologias Utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Swagger/OpenAPI
* Spring HATEOAS
* Spring Cache
* JUnit 5

---

## DTO e VO

Foram utilizados DTOs (Data Transfer Objects) para transferência de dados entre cliente e servidor, evitando exposição direta das entidades.

Exemplos:

* ProdutoRequestDTO
* ProdutoResponseDTO
* PedidoRequestDTO
* PedidoResponseDTO
* PagamentoRequestDTO
* PagamentoResponseDTO
---

## HATEOAS

A API implementa HATEOAS permitindo navegação dinâmica entre recursos através de links relacionados.

---

## Cache

Foi implementado cache utilizando Spring Cache para otimizar consultas frequentemente acessadas.

Anotações utilizadas:

* @Cacheable
* @CacheEvict
* @Caching
---

## Swagger

A documentação da API foi gerada automaticamente com Swagger/OpenAPI.

Acesso:

```
http://localhost:8080/swagger-ui/index.html#/
```

## Testes Automatizados

Foram implementados testes unitários utilizando JUnit 5 para validação das regras de negócio e funcionamento da aplicação.

Exemplos de testes:

* Cadastro de produto
* Validação de SKU duplicado
* Pagamento com valor incorreto
* Controle de estoque

---

## Exemplo de Consumo da API

### Criar Produto

POST `/produtos`

```json
{
  "sku": "ABC123",
  "nome": "Mouse",
  "precoVenda": 50.0,
  "unidadeMedida": "UN",
  "quantidadeEstoque": 10
}
```

### Criar Pedido

POST `/pedidos`

```json
{
  "dataPedido": "2026-06-15",
  "status": "ABERTO",
  "clienteId": 1
}
```

### Adicionar Item ao Pedido

POST `/itemPedidos`

```json
{
  "quantidade": 2,
  "precoUnitario": 50,
  "pedidoId": 1,
  "produtoId": 1
}
```

### Registrar Pagamento

POST `/pagamentos`

```json
{
  "metodoPagamento": "PIX",
  "dataConfirmacao": "2026-06-15",
  "statusPagamento": "APROVADO",
  "valorPago": 100,
  "pedidoId": 1
}
```

---

## Como Executar o Projeto

1. Clonar o repositório:

```bash
git clone URL_DO_REPOSITORIO
```

2. Configurar o banco MySQL.

3. Ajustar o arquivo:

```properties
application.properties
```

4. Executar:

```bash
./mvnw spring-boot:run
```

---

## Conclusão

O projeto permitiu aplicar conceitos fundamentais de desenvolvimento de APIs REST, persistência de dados, arquitetura em camadas e boas práticas de programação orientada a objetos.

Além disso, foram utilizados recursos avançados do ecossistema Spring, como HATEOAS, Cache, Swagger e testes automatizados, aproximando a solução de aplicações reais utilizadas no mercado.

---

## Repositório GitHub

```
https://github.com/Gabiszsz/api-estoque-pedidos.git
```
