# 🚀 Sistema de Gerenciamento de Vendas (API)

Este é um projeto acadêmico de um sistema de backend para gerenciamento de vendas, desenvolvido em Java com o framework Spring Boot. A aplicação expõe uma API RESTful para gerenciar clientes, produtos e pedidos, utilizando um banco de dados PostgreSQL para persistência.

---

## 1. Pré-requisitos

Antes de começar, certifique-se de que você tem os seguintes softwares instalados em sua máquina:

* [Java JDK 17 (ou superior)](https://www.oracle.com/java/technologies/downloads/#java17)
* [Apache Maven](https://maven.apache.org/download.cgi)
* [PostgreSQL (Banco de Dados)](https://www.postgresql.org/download/)
* Um cliente de API, como o [Postman](https://www.postman.com/downloads/), para testar os endpoints.

---

## 2. Configuração do Banco de Dados

A aplicação espera se conectar a um banco de dados PostgreSQL.

1.  Inicie o seu serviço do PostgreSQL.
2.  Crie um novo banco de dados para o projeto. O nome padrão esperado pela configuração é `projeto_vendas`.
    ```sql
    CREATE DATABASE projeto_vendas;
    ```
3.  As tabelas (`clientes`, `produtos`, `pedidos`, `itens_pedido`) serão criadas automaticamente pelo Spring Data JPA na primeira vez que a aplicação for executada.

---

## 3. Configuração da Aplicação (Obrigatório)

Por razões de segurança, o arquivo de configuração com as credenciais do banco não está incluído no repositório. Você deve criá-lo manualmente.

1.  Navegue até a pasta de recursos do projeto: `src/main/resources/`
2.  Crie um novo arquivo chamado exatamente: **`application.properties`**
3.  Copie e cole o conteúdo abaixo neste arquivo:

    ```properties
    # --- Configuração do Banco de Dados PostgreSQL ---
    # Altere "seu_usuario_postgres" e "sua_senha_postgres"
    # com as suas credenciais reais do PostgreSQL.
    
    spring.datasource.url=jdbc:postgresql://localhost:5432/projeto_vendas
    spring.datasource.username=seu_usuario_postgres
    spring.datasource.password=sua_senha_postgres
    
    # --- Configurações do JPA (Hibernate) ---
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
    ```

---

## 4. Executando a Aplicação

Com o banco de dados e o arquivo `application.properties` configurados, você pode iniciar o servidor:

**A. Pelo IntelliJ IDEA (Recomendado):**

1.  Abra a pasta do projeto no IntelliJ.
2.  Aguarde o Maven baixar as dependências.
3.  Encontre o arquivo `SistemaVendasApiApplication.java`.
4.  Clique no ícone "Play" (▶) ao lado do método `main` para iniciar o servidor.

**B. Pelo Terminal (Maven):**

Na pasta raiz do projeto (onde se encontra o `pom.xml`), execute:

```bash
mvn spring-boot:run
