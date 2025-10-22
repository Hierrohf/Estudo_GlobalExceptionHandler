# Projeto Exception 🎯

Este projeto foi desenvolvido como **estudo sobre tratamento de exceptions em APIs REST com Spring Boot**.  
O objetivo principal **não foi construir uma API completa de produção**, mas sim aprender e praticar:

- Criação de **exceptions customizadas** para regras de negócio (ex: `ClienteNaoEncontradoException`, `EmailJaCadastradoException`).
- Uso de um **GlobalExceptionHandler** centralizado com `@ControllerAdvice`.
- Estruturação de **mensagens de erro claras** para o consumidor da API.
- Separação em camadas (Controller, Service, Repository, DTO, Mapper).
- Boas práticas básicas em endpoints REST.

---

## 🚀 Tecnologias utilizadas
- Java 17+
- Spring Boot
- Spring Data JPA
- **Oracle Database rodando em container Docker**
- Maven

---

## ⚡ Funcionalidades implementadas
- Cadastro de cliente com validações (nome, e-mail, CPF).
- Busca de cliente por ID.
- Listagem de todos os clientes.
- Exclusão de cliente por ID.
- Tratamento de erros:
  - Cliente não encontrado → `404 Not Found`
  - Nome, CPF ou e-mail duplicados → `400 Bad Request`
  - Mensagens padronizadas via classe `ErrorMessage`.

---

## 📌 Status do projeto
Este projeto está **finalizado em sua primeira fase**, mesmo **incompleto como API real**.  
Isso porque o **objetivo inicial foi alcançado**: entender como estruturar e tratar exceptions em um projeto Spring Boot.  

⚠️ **Importante:** a ideia **não é abandonar o projeto**, mas sim **evoluir ele ao longo do tempo**.  
Hoje, os tratamentos implementados podem ser considerados **básicos**, mas isso abre espaço para novas práticas mais avançadas, como:
- Criação de hierarquia de exceptions mais elaborada.
- Tratamento de erros específicos de integração com banco de dados Oracle.
- Testes unitários e de integração para validação dos handlers.
- Documentação da API (Swagger/OpenAPI).
- Segurança/autenticação.
- Versionamento e novos cenários de negócio.

👉 Em outras palavras: **o laboratório continua**. Esse projeto ainda deve render boas evoluções no futuro.

A API estará disponível em:
👉 http://localhost:8080/clientes

⚠️ É necessário ter um container Oracle rodando no Docker e configurar as credenciais no application.properties.
---

## 🧑‍💻 Como rodar
```bash
# clonar repositório
git clone <url-do-repo>

# entrar na pasta do projeto
cd projetoexception

# rodar com Maven
./mvnw spring-boot:run


