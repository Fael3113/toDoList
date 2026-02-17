# Spring Boot - Todo List API

Aplicação de gerenciamento de tarefas desenvolvida durante o curso introdutório de Spring Boot da Rocketseat.

## Sobre o Projeto

API RESTful para gerenciamento de tarefas (Todo List) construída com Spring Boot, implementando autenticação básica, criptografia de senhas e persistência de dados com PostgreSQL.

## Arquitetura

O projeto segue uma arquitetura em camadas, organizando responsabilidades de forma clara e modular:

### Model
Camada responsável pela modelagem das entidades do banco de dados. Define a estrutura das tabelas através de classes anotadas com `@Entity(name = "nome_tabela")`. Contém os getters, setters (ou `@Data` do Lombok) e construtores conforme necessário.

### Repository
Camada de persistência de dados que fornece objetos para o Controller vindos do Model. Implementada como interface que estende `JpaRepository<ClasseModel, UUID/Long>`, provendo métodos CRUD automaticamente.

### Filter/Auth
Camada de controle de autorização e acesso à API. Responsável por:
- Obter e validar credenciais de usuário
- Realizar decode de senhas criptografadas
- Filtrar requisições por usuário autenticado
- Garantir que usuários acessem apenas suas próprias tarefas

### Utils
Camada contendo métodos utilitários do projeto. Exemplo: implementação de operação PATCH que preserva dados não atualizados mantendo informações já existentes no banco de dados em memória.

### Service
Camada onde residem as regras de negócio da aplicação. Em projetos mais simples, essa lógica pode ser incorporada diretamente no Controller, mas a separação é uma boa prática recomendada.

### Exception
Camada de tratamento de erros e exceções. Gerencia exceções que podem ser resolvidas pelo usuário, como:
- Inserção de tipos de dados incorretos
- Violação de limites de caracteres
- Validações de negócio

### Controller
Camada que gerencia o fluxo da aplicação, lidando com requisições HTTP. Principais características:

**Anotações principais:**
- `@RestController` ou `@Controller`: Define a classe como controlador REST
- `@RequestMapping("/rota")`: Define a rota base (evita repetição nos mappings filhos)
- `@GetMapping`, `@PostMapping`, `@DeleteMapping`, `@PatchMapping`, `@PutMapping`: Define operações HTTP

**Estrutura de rotas:**
```
localhost:8080/rota/proximaRota
```
As rotas funcionam em cascata, compondo o caminho completo.

**Injeção de dependências:**

Forma tutorial (não recomendada):
```java
@Autowired
private IUserRepository userRepository;
```

Boa prática (injeção por construtor):
```java
private final IUserRepository userRepository;

public UserController(IUserRepository userRepository) {
    this.userRepository = userRepository;
}
```

**ResponseEntity:**
Permite controle completo sobre a resposta HTTP, incluindo status code, corpo da resposta e headers, seguindo o formato esperado de uma API REST.

## Tecnologias

### Dependências Principais

**BCrypt**
Biblioteca para criptografia de senhas, garantindo que credenciais não fiquem expostas no banco de dados em texto plano.

**Lombok**
Reduz código boilerplate através de anotações, gerenciando automaticamente getters, setters, construtores e outros métodos comuns.

**Spring Data JPA**
Facilita a persistência de dados e operações com o banco de dados PostgreSQL.

## Deploy

A aplicação foi deployada com sucesso no Render utilizando Docker. Os principais desafios enfrentados foram:
- Formatação correta da URL de conexão do banco de dados
- Configuração adequada das variáveis de ambiente
- Integração entre Web Service e PostgreSQL

## Observações Importantes

O Spring Boot, por padrão e como boa prática em produção, não realiza alterações estruturais em bancos de dados já existentes (como adicionar constraints `UNIQUE`). Para modificações desse tipo, é necessário:
- Dropar e recriar a tabela via Spring Boot, ou
- Realizar alterações manualmente através do gerenciador do banco de dados (ex: pgAdmin para PostgreSQL)

## Endpoints da API

### Usuários
- `POST /users/criarUser` - Criar novo usuário
- `GET /users/verUser` - Ver todos os usuários

### Tarefas
- `GET /tasks/verTask` - Visualizar tasks do usuário autenticado
- `POST /tasks/criarTask` - Criar tarefas para o usuário autenticado
- `PATCH /tasks/patchTask{id}` - Atualizar item específico da tarefa, sem alterar os demais campos
- `PUT /tasks/putTask{id}` - Atualiza a tarefa como um todo. Cuidado! Se houver campo não informado, durante a atualização, ele será nulificado

## Autor

Desenvolvido por Rafael de Melo Santiago durante o curso de Spring Boot Introdutório da Rocketseat. Com pequenas modificações que achei conveniente durante o desenvolvimento, como utilizar um banco de dados persistente via PostgreSQL ao invés do H2 Databaase In-Memory
