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

### Produtos e Categorias

* O nome do produto é obrigatório.
* O preço de venda não pode ser negativo.
* A quantidade em estoque não pode ser negativa.
* Não é permitido cadastrar produtos com SKUs duplicados.
* Categorias podem ser associadas aos produtos para facilitar sua classificação.

### Clientes

* O CPF do cliente é obrigatório.
* O CPF deve ser único no sistema.
* O CPF deve conter exatamente 11 dígitos numéricos.
* O endereço completo do cliente é de preenchimento obrigatório.

### Fornecedores

* O CNPJ do fornecedor é obrigatório.
* O CNPJ deve ser único no sistema.
* O CNPJ deve conter exatamente 14 dígitos numéricos.
* Não é permitido cadastrar um fornecedor sem informar o contato do vendedor.

### Pedidos

* Todo novo pedido é iniciado automaticamente com o status **ABERTO**.
* Todo novo pedido inicia com valor total igual a **0,00**.
* O valor total do pedido é recalculado automaticamente sempre que itens são adicionados, atualizados ou removidos.
* Apenas pedidos com status **ABERTO** podem ser cancelados, alterando seu status para **CANCELADO**.

### Itens do Pedido

* A quantidade de um item deve ser sempre maior que zero.
* O preço unitário do produto é armazenado no momento da compra, preservando o histórico do pedido.
* Ao adicionar um item ao pedido, o estoque do produto é reduzido automaticamente.
* Ao remover ou atualizar um item do pedido, o estoque é recalculado e devolvido proporcionalmente.

### Pagamentos

* Apenas pedidos com status **ABERTO** podem receber pagamentos.
* O valor pago deve ser exatamente igual ao valor total do pedido.
* Após a confirmação do pagamento, o status do pedido é alterado automaticamente para **PAGO**.

### Estoque

* Ao registrar uma nova entrada de estoque, a data da entrada é armazenada automaticamente pelo sistema.
* Não é permitido remover uma entrada de estoque caso isso torne o saldo global do produto negativo.
* O sistema identifica situações de estoque baixo quando a quantidade atual é menor ou igual à quantidade mínima configurada.
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
