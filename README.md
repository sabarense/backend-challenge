# 🌟 Sistema de Gerenciamento de Notas e Frequência - Backend 📚

Este repositório contém o **backend** para o Sistema de Gerenciamento de Notas e Frequência. Ele oferece uma API RESTful para gerenciar os dados dos alunos, incluindo a criação de alunos, cálculo de médias de notas e frequência, e a visualização de alunos com desempenho excepcional ou crítico.

## 🚀 Funcionalidades

- **Cadastro de Alunos** 📝: Crie novos alunos com suas notas e frequência.
- **Listagem de Alunos** 👀: Consulte todos os alunos cadastrados, com suas notas e frequências.
- **Cálculo de Médias** 📊:
    - Média das notas de um aluno específico.
    - Média de notas da turma por disciplina.
    - Média de frequência da turma.

- **Destaques** 🌟:
    - Alunos com média **acima da turma**.
    - Alunos com frequência **abaixo de 75%**.

## 🛠️ Tecnologias Usadas

- **Spring Boot** 🌱: Framework Java para criação de aplicações backend.
- **Spring Data JPA** 📊: Para interação com o banco de dados.
- **H2 Database** 💾: Banco de dados em memória para desenvolvimento.
- **DTO (Data Transfer Object)** 💻: Para transferência de dados entre camadas.
- **Exceções Customizadas** ⚠️: Implementação de exceções específicas para tratar erros no sistema.
- **SLF4J** 📝: Logger para monitoramento e rastreamento de operações.

## ⚙️ Instalação e Execução

### Pré-requisitos

1. **JDK 11 ou superior**: Para compilar e executar a aplicação.
2. **Maven**: Para gerenciamento de dependências e execução do projeto.

### Passos para Execução 🚶‍♂️

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/sabarense/backend-challenge
   cd backend-challenge

2. **Compilar o projeto**:
    ```bash
    mvn clean install
    ```
3. **Executar o projeto:**
   ```bash
   mvn spring-boot:run

## 📚 Estrutura do Projeto

A aplicação está estruturada da seguinte forma:

- **src/main/java/com/example/challenge/controller**: Contém os controladores que expõem os endpoints da API.
  - `AlunoController.java`: Controlador principal para gerenciar alunos.

- **src/main/java/com/example/challenge/service**: Contém a lógica de negócios da aplicação.
  - `AlunoService.java`: Serviço que manipula os dados dos alunos.

- **src/main/java/com/example/challenge/dto**: Contém os objetos de transferência de dados (DTO).
  - `AlunoRequestDTO.java`: DTO para representar os dados de entrada de um aluno.

- **src/main/java/com/example/challenge/exception**: Contém as exceções customizadas.
  - `AlunoNotFoundException.java`: Exceção lançada quando um aluno não é encontrado.
  - `FrequenciaInvalidaException.java`: Exceção para frequência inválida.
  - `NotasInvalidasException.java`: Exceção para notas fora do intervalo permitido.
  - `NumeroNotasInvalidoException.java`: Exceção para número de notas inválido.

- **src/main/java/com/example/challenge/repository**: Contém os repositórios para interação com o banco de dados.
  - `AlunoRepository.java`: Repositório para persistência dos dados de alunos.

## 🧑‍💻 Endpoints da API

### 1. **POST /alunos** - Criar um novo aluno
- Corpo da requisição (JSON):
  ```json
  {
    "nome": "João",
    "notas": [8.5, 7.0, 9.0, 6.5, 7.5],
    "frequencia": 80
  }

### 2. **GET /alunos** -  Listar todos os alunos

### 3. **GET /alunos/frequencia/{id}** - Obter a frequência de um aluno
* Parâmetros:
  ID do aluno.
### 4. **GET /alunos/media-frequencia-turma** - Calcular a média de frequência da turma

### 5. **PUT /alunos/notas/{id}** - Atualizar as notas de um aluno
* Parâmetros:
  ID do aluno.
- Corpo da requisição (JSON):
  ```json
  {
    "notas": [9.0, 8.0, 7.5, 6.0, 8.0],
    "frequencia": 85
  }
### 6. GET /alunos/media-notas/{id} - Calcular a média de notas de um aluno

### 7. **GET /alunos/media-notas-turma-disciplina** - Calcular a média de notas da turma por disciplina

### 8. **GET /alunos/acima-media-turma** - Obter alunos com notas acima da média da turma

### 9. **GET /alunos/abaixo-frequencia** - Obter alunos com frequência abaixo de 75%

## Contribuições 🤝
Se você quiser contribuir com o projeto, fique à vontade para abrir issues e pull requests.

## Licença 📄
Este projeto está licenciado sob a MIT License.