# 🚀 Sistema de Gerenciamento de Vendas (API)

Aplicação RESTful em Spring Boot para gerenciar clientes, produtos e pedidos com persistência em PostgreSQL. Inclui validação de dados, regras de estoque e endpoints prontos para testes manuais ou automatizados.

---

## 1. Pré-requisitos

Instale os seguintes softwares:

- [Java JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)
- Cliente HTTP (ex.: [Postman](https://www.postman.com/downloads/))

---

## 2. Banco de Dados

1. Inicie o serviço do PostgreSQL.
2. Crie o banco padrão esperado pelo projeto (ajuste se desejar):
   ```sql
   CREATE DATABASE projeto_vendas;
   ```
3. As tabelas (`clientes`, `produtos`, `pedidos`, `itens_pedido`) são criadas automaticamente na primeira execução graças ao Spring Data JPA.

---

## 3. Configuração da Aplicação

O arquivo `src/main/resources/application.properties` já está versionado. Atualize conforme o seu ambiente:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/projeto_vendas
spring.datasource.username=postgres
spring.datasource.password=12345
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

> **Observação:** ajuste porta, usuário e senha para combinar com a sua instalação. Os endpoints usam Bean Validation (`spring-boot-starter-validation`); payloads inválidos retornam `400 Bad Request` com mensagens indicando cada campo.

---

## 4. Como executar

Na raiz do projeto:

```bash
mvn spring-boot:run
```

Ou, na IDE, execute a classe `SistemaVendasApiApplication`.

---

## 5. Endpoints

Base URL padrão: `http://localhost:8080`

### Clientes

- `POST /clientes`
  ```json
  {
    "nome": "João Silva",
    "email": "joao@exemplo.com",
    "telefone": "11999999999"
  }
  ```
  - Validações: nome, email e telefone obrigatórios; email precisa ser válido.
  - Exemplo de retorno inválido:
    ```json
    {
      "nome": "O nome é obrigatório.",
      "email": "O e-mail informado é inválido."
    }
    ```
- `GET /clientes` – lista todos
- `GET /clientes/{id}` – retorna 200 ou 404
- `PUT /clientes/{id}` – atualiza (reaplica validações)
- `DELETE /clientes/{id}` – remove cliente (204)

### Produtos

- `POST /produtos`
  ```json
  {
    "nome": "Notebook Gamer",
    "descricao": "Core i7, 16GB RAM",
    "preco": 4500.00,
    "quantidadeEstoque": 12
  }
  ```
  - Validações: nome obrigatório, preço > 0, estoque ≥ 0.
  - Retorno para payload inválido:
    ```json
    {
      "nome": "O nome do produto é obrigatório.",
      "preco": "O preço deve ser maior que zero.",
      "quantidadeEstoque": "A quantidade em estoque não pode ser negativa."
    }
    ```
- `GET /produtos` – lista todos
- `GET /produtos/{id}` – 200 ou 404
- `PUT /produtos/{id}` – atualiza com validações
- `DELETE /produtos/{id}` – remove produto (204)

### Pedidos

- `POST /pedidos`
  ```json
  {
    "clienteId": 1,
    "itens": [
      { "produtoId": 1, "quantidade": 2 }
    ]
  }
  ```
  - Regras de negócio: cliente e produtos precisam existir; estoque é verificado e decrementado.
- `GET /pedidos` – lista todos os pedidos.

---

## 6. Testes com Postman

1. Crie uma collection contendo as requisições acima e defina o header `Content-Type: application/json`.
2. Monte casos positivos e negativos:
   - Cliente com dados vazios → `400`.
   - Produto com preço negativo → `400`.
   - Pedido com estoque insuficiente → `400`.
3. Use a aba **Tests** para automatizar checks simples:
   ```javascript
   pm.test("Status 201", function () {
     pm.response.to.have.status(201);
   });
   ```

---

## 7. Testes Automatizados

O projeto possui configuração de testes com H2 em `src/test/resources/application.properties`. Para executar:

```bash
mvn test
```

---

## 8. Dependências relevantes

- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `org.postgresql:postgresql`
- `com.h2database:h2` (escopo de testes)

---

## 9. Próximos passos sugeridos

- Adicionar paginação e filtros nas listagens.
- Criar testes de integração cobrindo fluxos de pedidos.
- Centralizar tratamento de erros com um handler global reutilizável.

