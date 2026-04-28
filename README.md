# Gerenciador de Eventos — Projeto Diamante 02

API REST desenvolvida com **Spring Boot 3**, **Spring Data JPA** e banco **H2 em memória** para o projeto Diamante 02 Java Advanced — FIAP.

---

## 🎯 Objetivo

Gerenciar eventos, participantes e inscrições, permitindo CRUD completo, buscas com filtros, paginação, ordenação e projections.

---

## 🗂️ Entidades e Relacionamentos

| Entidade | Descrição |
|---|---|
| **Evento** | Representa um evento (nome, data, local, tipo, capacidade) |
| **Participante** | Pessoa que pode se inscrever em eventos |
| **Inscrição** | Relacionamento N:N entre Evento e Participante com status |

- Um **Participante** pode ter muitas **Inscrições**
- Um **Evento** pode ter muitas **Inscrições**
- Cada par (evento, participante) é único (constraint no banco)

---

## 🚀 Como executar

```bash
# Clonar o repositório
git clone https://github.com/pietroabrahamian/diamante2.git
cd gerenciadorevento

# Executar
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`

H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:eventodb`
- User: `sa` | Password: *(vazio)*

---

## 📋 Endpoints Principais

### Eventos — `/eventos`
| Método | URL | Descrição |
|---|---|---|
| GET | `/eventos` | Listar todos (paginado) |
| GET | `/eventos/{id}` | Buscar por ID |
| POST | `/eventos` | Criar evento |
| PUT | `/eventos/{id}` | Atualizar evento |
| DELETE | `/eventos/{id}` | Deletar evento |
| GET | `/eventos/resumo` | Projection com campos resumidos |
| GET | `/eventos/busca/nome?nome=` | Filtrar por nome |
| GET | `/eventos/busca/tipo?tipo=` | Filtrar por tipo |
| GET | `/eventos/busca/local?local=` | Filtrar por local |
| GET | `/eventos/busca/periodo?inicio=&fim=` | Filtrar por período |
| GET | `/eventos/busca/capacidade?minimo=` | Filtrar por capacidade mínima |

### Participantes — `/participantes`
| Método | URL | Descrição |
|---|---|---|
| GET | `/participantes` | Listar todos (paginado) |
| GET | `/participantes/{id}` | Buscar por ID |
| POST | `/participantes` | Criar participante |
| PUT | `/participantes/{id}` | Atualizar participante |
| DELETE | `/participantes/{id}` | Deletar participante |
| GET | `/participantes/resumo` | Projection com id, nome e email |
| GET | `/participantes/busca/email?email=` | Buscar por e-mail |
| GET | `/participantes/busca/nome?nome=` | Filtrar por nome |
| GET | `/participantes/busca/idade?min=&max=` | Filtrar por faixa etária |

### Inscrições — `/inscricoes`
| Método | URL | Descrição |
|---|---|---|
| GET | `/inscricoes` | Listar todas (paginado) |
| GET | `/inscricoes/{id}` | Buscar por ID |
| POST | `/inscricoes` | Criar inscrição |
| PUT | `/inscricoes/{id}` | Atualizar inscrição |
| PATCH | `/inscricoes/{id}/status?novoStatus=` | Atualizar somente o status |
| DELETE | `/inscricoes/{id}` | Cancelar/deletar inscrição |
| GET | `/inscricoes/busca/evento/{eventoId}` | Inscrições de um evento |
| GET | `/inscricoes/busca/participante/{participanteId}` | Inscrições de um participante |
| GET | `/inscricoes/busca/status?status=` | Filtrar por status |

---

## 🧪 Tipos válidos para Evento

`Workshop` | `Palestra` | `Seminário` | `Congresso` | `Show` | `Outro`

## 📌 Status válidos para Inscrição

`CONFIRMADA` | `PENDENTE` | `CANCELADA`
