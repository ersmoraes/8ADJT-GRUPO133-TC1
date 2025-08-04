# Projeto Fase 1 Pós Graduação Arquitetura e Desenvolvidomento Java

# Restaurante API

Uma API RESTful para gerenciamento de restaurantes, desenvolvida com Spring Boot, seguindo os princípios de Clean Architecture e Domain-Driven Design (DDD).

## 📋 Sobre o Projeto

Esta API permite o gerenciamento completo de restaurantes, incluindo cadastro de usuários (proprietários de restaurantes e clientes), autenticação e autorização através de JWT, e operações CRUD para as entidades principais do sistema.

## 🛠️ Tecnologias Utilizadas

- **Spring Boot**: Framework para criação de aplicações Java
- **Spring Security**: Para autenticação e autorização
- **Spring Data JPA**: Para persistência de dados
- **H2 Database**: Banco de dados em memória para ambiente de desenvolvimento
- **Flyway**: Para migrações de banco de dados
- **Swagger/OpenAPI**: Para documentação da API
- **Docker**: Para containerização da aplicação
- **JUnit & Mockito**: Para testes automatizados

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas baseada nos princípios de Clean Architecture:

- **API Layer**: Controllers, DTOs e tratamento de exceções
- **Domain Layer**: Modelos de domínio, interfaces de serviços e repositórios
- **Service Layer**: Implementações de serviços e regras de negócio
- **Repository Layer**: Persistência de dados e comunicação com o banco

## 🚀 Como Executar

### Pré-requisitos

- JDK 17+
- Maven 3.6+
- Docker (opcional)

### Execução Local

1. Clone o repositório:
   ```bash
   git clone https://github.com/ersmoraes/8ADJT-GRUPO133-TC1.git
   cd restaurante-api
   ```

2. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

3. A aplicação estará disponível em:
   ```
   http://localhost:8080/api/
   ```

### Execução com Docker

1. Construa a imagem Docker:
   ```bash
   docker build -t restaurante-api -f docker/Dockerfile .
   ```

2. Execute o container:
   ```bash
   docker-compose -f docker/docker-compose.yml up
   ```

## 📊 Perfis de Configuração

- **dev**: Utiliza banco H2 em memória, com console habilitado e logs detalhados
- **prod**: Configurado para ambiente de produção (requer configuração adicional de banco de dados)

## 🔒 Segurança

A API utiliza JWT (JSON Web Token) para autenticação e autorização. Para acessar endpoints protegidos:

1. Obtenha um token através do endpoint de login
2. Inclua o token no header `Authorization` das requisições subsequentes

## 📓 Documentação da API

A documentação completa da API está disponível através do Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

## 🧪 Testes

Para executar os testes:

```bash
./mvnw test
```

### E a página do jacoco, que mostra a cobertura de testes, está disponível em:

```
/target/site/jacoco/index.html
```

## 🐞 Solução de Problemas

### Erros comuns

#### Erro de inicialização do JPA/EntityManagerFactory

Se ocorrer erro na inicialização do EntityManagerFactory:

1. Verifique as configurações de banco de dados em `application.yml` ou `application-dev.yml`
2. Confirme se as migrações Flyway estão corretas e correspondem às entidades JPA
3. Temporariamente altere `spring.jpa.hibernate.ddl-auto` para `create` ou `update` para diagnosticar problemas

#### Console H2

O console H2 está disponível em desenvolvimento em:

```
http://localhost:8080/api/h2-console
```

Configurações de conexão:
- JDBC URL: `jdbc:h2:mem:restaurantedb`
- Usuário: `sa`
- Senha: (vazio)
