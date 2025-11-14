# 📚 Documentação Técnica - Sistema de Gerenciamento de Vendas (API)

## 📋 Índice

1. [Visão Geral](#visão-geral)
2. [Arquitetura do Sistema](#arquitetura-do-sistema)
3. [Stack Tecnológico](#stack-tecnológico)
4. [Estrutura do Projeto](#estrutura-do-projeto)
5. [Modelo de Dados](#modelo-de-dados)
6. [Endpoints da API](#endpoints-da-api)
7. [Validações e Regras de Negócio](#validações-e-regras-de-negócio)
8. [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
9. [Tratamento de Erros](#tratamento-de-erros)
10. [Padrões e Boas Práticas](#padrões-e-boas-práticas)

---

## 🎯 Visão Geral

O **Sistema de Gerenciamento de Vendas** é uma API RESTful desenvolvida em Java utilizando o framework Spring Boot. A aplicação permite o gerenciamento completo de clientes, produtos e pedidos, com controle de estoque automático e validações robustas de dados.

### Funcionalidades Principais

- ✅ CRUD completo de Clientes
- ✅ CRUD completo de Produtos
- ✅ Criação e listagem de Pedidos
- ✅ Controle automático de estoque
- ✅ Validação de dados com Bean Validation
- ✅ Tratamento de erros padronizado
- ✅ API RESTful seguindo convenções HTTP

---

## 🏗️ Arquitetura do Sistema

O projeto segue o padrão de arquitetura em camadas (Layered Architecture), separando responsabilidades de forma clara:

```
┌─────────────────────────────────────┐
│      Controller Layer (REST)        │  ← Recebe requisições HTTP
├─────────────────────────────────────┤
│      Service Layer (Business)       │  ← Lógica de negócio
├─────────────────────────────────────┤
│      Repository Layer (Data)        │  ← Acesso aos dados
├─────────────────────────────────────┤
│      Model Layer (Entity)           │  ← Entidades JPA
└─────────────────────────────────────┘
```

### Camadas

1. **Controller**: Recebe requisições HTTP, valida entrada e retorna respostas
2. **Service**: Contém a lógica de negócio (ex: criação de pedidos, controle de estoque)
3. **Repository**: Interface com o banco de dados usando Spring Data JPA
4. **Model**: Entidades JPA que representam as tabelas do banco
5. **DTO**: Data Transfer Objects para transferência de dados entre camadas

---

## 💻 Stack Tecnológico

### Backend
- **Java 17**: Linguagem de programação
- **Spring Boot 3.5.7**: Framework principal
- **Spring Data JPA**: Persistência de dados
- **Spring Web**: Construção de APIs REST
- **Bean Validation**: Validação de dados

### Banco de Dados
- **PostgreSQL**: Banco de dados relacional (produção)
- **H2 Database**: Banco em memória (testes)

### Ferramentas
- **Maven**: Gerenciamento de dependências e build
- **Hibernate**: ORM (Object-Relational Mapping)

### Dependências Principais

```xml
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- postgresql (runtime)
- h2 (test scope)
```

---

## 📁 Estrutura do Projeto

```
projeto-java-vendas/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/sistema_vendas_api/
│   │   │       ├── SistemaVendasApiApplication.java
│   │   │       ├── controller/          # Camada de controle REST
│   │   │       │   ├── ClienteController.java
│   │   │       │   ├── ProdutoController.java
│   │   │       │   ├── PedidoController.java
│   │   │       │   └── TesteController.java
│   │   │       ├── service/             # Camada de serviços
│   │   │       │   └── PedidoService.java
│   │   │       ├── repository/          # Camada de acesso a dados
│   │   │       │   ├── ClienteRepository.java
│   │   │       │   ├── ProdutoRepository.java
│   │   │       │   ├── PedidoRepository.java
│   │   │       │   └── ItemPedidoRepository.java
│   │   │       ├── model/               # Entidades JPA
│   │   │       │   ├── Cliente.java
│   │   │       │   ├── Produto.java
│   │   │       │   ├── Pedido.java
│   │   │       │   └── ItemPedido.java
│   │   │       └── dto/                 # Data Transfer Objects
│   │   │           ├── PedidoDTO.java
│   │   │           └── ItemPedidoDTO.java
│   │   └── resources/
│   │       └── application.properties   # Configurações
│   └── test/
│       └── java/                        # Testes unitários
├── pom.xml                              # Configuração Maven
├── README.md                            # Documentação do usuário
└── DOCUMENTACAO_TECNICA.md             # Esta documentação
```

---

## 🗄️ Modelo de Dados

### Diagrama de Entidades

```
┌─────────────┐         ┌─────────────┐
│   Cliente   │         │   Produto   │
├─────────────┤         ├─────────────┤
│ id_cliente  │         │ id_produto  │
│ nome        │         │ nome        │
│ email       │         │ descricao   │
│ telefone    │         │ preco       │
└──────┬──────┘         │ quantidade_ │
       │                │   estoque   │
       │                └──────┬──────┘
       │                       │
       │                ┌──────┴──────┐
       │                │ ItemPedido  │
       │                ├─────────────┤
       │                │ id_item     │
       │                │ quantidade  │
       │                │ preco_unit  │
       │                └──────┬──────┘
       │                       │
┌──────┴──────┐                │
│   Pedido    │◄───────────────┘
├─────────────┤
│ id_pedido   │
│ id_cliente  │
│ data_pedido │
│ status      │
└─────────────┘
```

### Entidades

#### 1. Cliente
```java
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank
    @Size(max = 120)
    private String nome;
    
    @NotBlank
    @Email
    @Size(max = 150)
    private String email;
    
    @NotBlank
    @Size(min = 8, max = 20)
    private String telefone;
}
```

**Tabela**: `clientes`
- `id_cliente` (PK, auto-incremento)
- `nome` (NOT NULL, VARCHAR(120))
- `email` (NOT NULL, VARCHAR(150))
- `telefone` (NOT NULL, VARCHAR(20))

#### 2. Produto
```java
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank
    @Size(max = 120)
    private String nome;
    
    @Size(max = 255)
    private String descricao;
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal preco;
    
    @PositiveOrZero
    private int quantidadeEstoque;
}
```

**Tabela**: `produtos`
- `id_produto` (PK, auto-incremento)
- `nome` (NOT NULL, VARCHAR(120))
- `descricao` (VARCHAR(255))
- `preco` (NOT NULL, DECIMAL)
- `quantidade_estoque` (NOT NULL, INTEGER)

#### 3. Pedido
```java
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    private LocalDateTime dataPedido;
    private String status;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
    
    public BigDecimal getValorTotal() { ... }
}
```

**Tabela**: `pedidos`
- `id_pedido` (PK, auto-incremento)
- `id_cliente` (FK → clientes.id_cliente)
- `data_pedido` (TIMESTAMP)
- `status` (VARCHAR)

#### 4. ItemPedido
```java
@Entity
@Table(name = "itens_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;
    
    private Integer quantidade;
    private BigDecimal precoUnitario;
    
    public BigDecimal getSubtotal() { ... }
}
```

**Tabela**: `itens_pedido`
- `id_item_pedido` (PK, auto-incremento)
- `id_pedido` (FK → pedidos.id_pedido)
- `id_produto` (FK → produtos.id_produto)
- `quantidade` (NOT NULL, INTEGER)
- `preco_unitario` (NOT NULL, DECIMAL)

### Relacionamentos

- **Cliente ↔ Pedido**: 1:N (Um cliente pode ter vários pedidos)
- **Pedido ↔ ItemPedido**: 1:N (Um pedido pode ter vários itens)
- **Produto ↔ ItemPedido**: 1:N (Um produto pode estar em vários itens)

---

## 🌐 Endpoints da API

### Base URL
```
http://localhost:8080
```

### Clientes (`/clientes`)

| Método | Endpoint | Descrição | Status Codes |
|--------|----------|-----------|--------------|
| GET | `/clientes` | Lista todos os clientes | 200 OK |
| GET | `/clientes/{id}` | Busca cliente por ID | 200 OK, 404 Not Found |
| POST | `/clientes` | Cria um novo cliente | 201 Created, 400 Bad Request |
| PUT | `/clientes/{id}` | Atualiza um cliente | 200 OK, 404 Not Found, 400 Bad Request |
| DELETE | `/clientes/{id}` | Remove um cliente | 204 No Content |

#### Exemplos de Requisição

**POST /clientes**
```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "telefone": "11988887777"
}
```

**Resposta (201 Created)**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "telefone": "11988887777"
}
```

### Produtos (`/produtos`)

| Método | Endpoint | Descrição | Status Codes |
|--------|----------|-----------|--------------|
| GET | `/produtos` | Lista todos os produtos | 200 OK |
| GET | `/produtos/{id}` | Busca produto por ID | 200 OK, 404 Not Found |
| POST | `/produtos` | Cria um novo produto | 201 Created, 400 Bad Request |
| PUT | `/produtos/{id}` | Atualiza um produto | 200 OK, 404 Not Found, 400 Bad Request |
| DELETE | `/produtos/{id}` | Remove um produto | 204 No Content |

#### Exemplos de Requisição

**POST /produtos**
```json
{
  "nome": "Notebook Gamer",
  "descricao": "Core i7, 16GB RAM, SSD 512GB",
  "preco": 4500.00,
  "quantidadeEstoque": 10
}
```

**Resposta (201 Created)**
```json
{
  "id": 1,
  "nome": "Notebook Gamer",
  "descricao": "Core i7, 16GB RAM, SSD 512GB",
  "preco": 4500.00,
  "quantidadeEstoque": 10
}
```

### Pedidos (`/pedidos`)

| Método | Endpoint | Descrição | Status Codes |
|--------|----------|-----------|--------------|
| GET | `/pedidos` | Lista todos os pedidos | 200 OK |
| POST | `/pedidos` | Cria um novo pedido | 201 Created, 400 Bad Request |

#### Exemplos de Requisição

**POST /pedidos**
```json
{
  "clienteId": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    },
    {
      "produtoId": 2,
      "quantidade": 1
    }
  ]
}
```

**Resposta (201 Created)**
```json
{
  "id": 1,
  "cliente": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "telefone": "11988887777"
  },
  "dataPedido": "2025-11-12T10:30:00",
  "status": "PENDENTE",
  "itens": [
    {
      "id": 1,
      "produto": { ... },
      "quantidade": 2,
      "precoUnitario": 4500.00
    }
  ]
}
```

---

## ✅ Validações e Regras de Negócio

### Validações de Cliente

| Campo | Validação | Mensagem de Erro |
|-------|-----------|------------------|
| `nome` | @NotBlank, @Size(max=120) | "O nome é obrigatório." / "O nome deve ter no máximo 120 caracteres." |
| `email` | @NotBlank, @Email, @Size(max=150) | "O e-mail é obrigatório." / "O e-mail informado é inválido." |
| `telefone` | @NotBlank, @Size(min=8, max=20) | "O telefone é obrigatório." / "O telefone deve ter entre 8 e 20 caracteres." |

### Validações de Produto

| Campo | Validação | Mensagem de Erro |
|-------|-----------|------------------|
| `nome` | @NotBlank, @Size(max=120) | "O nome do produto é obrigatório." |
| `descricao` | @Size(max=255) | "A descrição deve ter no máximo 255 caracteres." |
| `preco` | @NotNull, @DecimalMin(0.0, inclusive=false) | "O preço é obrigatório." / "O preço deve ser maior que zero." |
| `quantidadeEstoque` | @PositiveOrZero | "A quantidade em estoque não pode ser negativa." |

### Regras de Negócio

#### Criação de Pedidos

1. **Cliente deve existir**: O `clienteId` informado deve existir no banco de dados
2. **Produto deve existir**: Todos os `produtoId` informados devem existir
3. **Estoque suficiente**: A quantidade solicitada não pode exceder o estoque disponível
4. **Atualização automática de estoque**: Ao criar um pedido, o estoque é automaticamente reduzido
5. **Status inicial**: Todo pedido é criado com status "PENDENTE"
6. **Data automática**: A data do pedido é definida automaticamente como a data/hora atual

#### Exemplo de Erro (400 Bad Request)

```json
{
  "error": "Estoque insuficiente para o produto: Notebook Gamer"
}
```

---

## 🗃️ Configuração do Banco de Dados

### PostgreSQL (Produção)

O arquivo `application.properties` deve conter:

```properties
# Configuração do Banco de Dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5433/projeto_vendas
spring.datasource.username=postgres
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

# Configurações do JPA (Hibernate)
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### H2 (Testes)

Configuração automática para testes unitários usando banco em memória.

### Estratégia de Criação de Tabelas

- **`ddl-auto=update`**: O Hibernate cria/atualiza as tabelas automaticamente baseado nas entidades JPA
- **Primeira execução**: Cria todas as tabelas necessárias
- **Execuções subsequentes**: Atualiza o schema se houver mudanças nas entidades

---

## ⚠️ Tratamento de Erros

### Códigos de Status HTTP

| Código | Significado | Quando Ocorre |
|--------|-------------|---------------|
| 200 | OK | Operação bem-sucedida (GET, PUT) |
| 201 | Created | Recurso criado com sucesso (POST) |
| 204 | No Content | Recurso removido com sucesso (DELETE) |
| 400 | Bad Request | Dados inválidos ou regra de negócio violada |
| 404 | Not Found | Recurso não encontrado |
| 500 | Internal Server Error | Erro interno do servidor |

### Estrutura de Resposta de Erro

#### Validação (400 Bad Request)

```json
{
  "nome": "O nome é obrigatório.",
  "email": "O e-mail informado é inválido.",
  "telefone": "O telefone é obrigatório."
}
```

#### Recurso Não Encontrado (404 Not Found)

```json
{
  "timestamp": "2025-11-12T10:30:00",
  "status": 404,
  "error": "Not Found",
  "path": "/clientes/999"
}
```

### Exception Handlers

O `ClienteController` possui handlers para:
- `MethodArgumentNotValidException`: Erros de validação de campos
- `ConstraintViolationException`: Violações de constraints

---

## 🎨 Padrões e Boas Práticas

### 1. Padrão Repository
- Uso de `JpaRepository` para abstrair acesso a dados
- Métodos padrão: `findAll()`, `findById()`, `save()`, `deleteById()`

### 2. Padrão DTO (Data Transfer Object)
- `PedidoDTO` e `ItemPedidoDTO` para transferência de dados
- Separação entre modelo de domínio e modelo de API

### 3. Transações
- Uso de `@Transactional` em operações que modificam múltiplas entidades
- Garantia de atomicidade (tudo ou nada)

### 4. Validação em Camadas
- Validação no Controller com `@Valid`
- Validação no Model com Bean Validation
- Validação de negócio no Service

### 5. Convenções REST
- URLs no plural (`/clientes`, `/produtos`, `/pedidos`)
- Métodos HTTP apropriados (GET, POST, PUT, DELETE)
- Códigos de status HTTP semânticos

### 6. Nomenclatura
- Classes: PascalCase (`ClienteController`)
- Métodos: camelCase (`findById`)
- Tabelas: snake_case (`clientes`, `itens_pedido`)
- Colunas: snake_case (`id_cliente`, `data_pedido`)

---

## 🔧 Desenvolvimento

### Executar a Aplicação

```bash
# Usando Maven Wrapper
./mvnw spring-boot:run

# Ou usando Maven instalado
mvn spring-boot:run
```

### Executar Testes

```bash
./mvnw test
```

### Build do Projeto

```bash
./mvnw clean package
```

---

## 📝 Notas Técnicas

### Performance
- Uso de `FetchType.EAGER` em `Pedido.itens` para carregar itens junto com o pedido
- Considerar `FetchType.LAZY` para grandes volumes de dados

### Segurança
- ⚠️ **Atenção**: O arquivo `application.properties` com credenciais não deve ser commitado
- Implementar autenticação/autorização em produção
- Usar HTTPS em produção

### Melhorias Futuras
- Implementar paginação nas listagens
- Adicionar filtros e busca
- Implementar autenticação JWT
- Adicionar documentação Swagger/OpenAPI
- Implementar testes unitários e de integração
- Adicionar logging estruturado
- Implementar cache para consultas frequentes

---

## 📞 Suporte

Para dúvidas ou problemas, consulte:
- [README.md](README.md) - Documentação do usuário
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)

---

**Versão da Documentação**: 1.0  
**Última Atualização**: Novembro 2025

