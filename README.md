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

server.port=8081
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

Base URL padrão: `http://localhost:8081`

> **Nota:** A aplicação está configurada para rodar na porta 8081. A interface web está disponível em `http://localhost:8081`.

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

## 9. Interface Web

A aplicação possui uma interface web moderna e responsiva desenvolvida com Bootstrap 5 e Thymeleaf, proporcionando uma experiência de usuário intuitiva para gerenciar clientes, produtos e pedidos.

### 🏠 Página Inicial

A página inicial apresenta um dashboard com acesso rápido às principais funcionalidades do sistema:

<img width="1338" height="632" alt="image" src="https://github.com/user-attachments/assets/5cd743e7-8d02-4a28-9a60-c0b7038ad37f" />


**Características:**
- Design moderno com gradiente roxo e animação de partículas no fundo
- Cards interativos para navegação rápida
- Layout responsivo que se adapta a diferentes tamanhos de tela
- Header centralizado com ícone e descrição do sistema

### 👥 Gerenciamento de Clientes

Interface completa para cadastro e gerenciamento de clientes:

<img width="1319" height="631" alt="image" src="https://github.com/user-attachments/assets/9191a168-eed1-440d-b55b-f510674c4149" />



**Funcionalidades:**
- Formulário de cadastro com validação em tempo real
- Lista de clientes em formato de tabela
- Botões de edição e exclusão para cada cliente
- Layout em duas colunas (formulário e lista)

**Exemplo de uso:**
1. Preencha o formulário com nome, e-mail e telefone
2. Clique em "Cadastrar Cliente"
3. O cliente aparece automaticamente na lista
4. Use os botões "Editar" ou "Excluir" para gerenciar

### 📦 Gerenciamento de Produtos

Controle completo do catálogo de produtos e estoque:

<img width="1288" height="639" alt="image" src="https://github.com/user-attachments/assets/decd88f4-fc96-4716-859a-cdfcc3404d06" />




**Funcionalidades:**
- Cadastro de produtos com nome, descrição, preço e quantidade em estoque
- Lista completa de produtos cadastrados
- Edição e exclusão de produtos
- Validação de preços e estoque

**Exemplo de uso:**
1. Preencha os dados do produto (nome, descrição, preço, estoque)
2. Clique em "Cadastrar Produto"
3. O produto é adicionado ao catálogo
4. Gerencie produtos existentes através dos botões de ação

### 🛍️ Gerenciamento de Pedidos

Criação e acompanhamento de pedidos de venda:

<img width="1292" height="620" alt="image" src="https://github.com/user-attachments/assets/8049b87c-9f42-4ff8-ad36-28166e384129" />


**Funcionalidades:**
- Seleção de cliente para o pedido
- Adição de múltiplos itens ao pedido
- Seleção de produto e quantidade para cada item
- Lista de todos os pedidos criados
- Validação automática de estoque

**Exemplo de uso:**
1. Selecione um cliente no dropdown
2. Escolha um produto e informe a quantidade
3. Clique em "+ Adicionar Item" para adicionar mais produtos
4. Clique em "Criar Pedido" para finalizar
5. O sistema valida o estoque automaticamente

### 🎨 Design e Experiência do Usuário

**Características visuais:**
- **Cores:** Gradiente roxo moderno (#667eea a #764ba2)
- **Animações:** Partículas flutuantes sutis no fundo
- **Tipografia:** Fonte Segoe UI para melhor legibilidade
- **Cards:** Efeitos de hover e sombras suaves
- **Responsividade:** Layout adaptável para mobile, tablet e desktop

**Componentes:**
- Botões com gradiente e efeitos de hover
- Formulários com validação visual
- Tabelas responsivas com scroll horizontal em telas pequenas
- Footer com informações da equipe e links para GitHub

### 📱 Responsividade

A aplicação é totalmente responsiva, adaptando-se perfeitamente a diferentes dispositivos:

- **Desktop:** Layout em duas colunas para formulários e listas
- **Tablet:** Layout adaptado mantendo usabilidade
- **Mobile:** Layout em coluna única com elementos empilhados

---

## 10. Próximos passos sugeridos

- Adicionar paginação e filtros nas listagens.
- Criar testes de integração cobrindo fluxos de pedidos.
- Centralizar tratamento de erros com um handler global reutilizável.

