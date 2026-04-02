# REST API with Spring Boot & PostgreSQL (Supabase)

A RESTful web API built with Java and Spring Boot, backed by a PostgreSQL database hosted on Supabase. Demonstrates core backend development concepts including JPA entities, repository pattern, and full CRUD operations.

## Features

- Full CRUD REST API using Spring Web
- PostgreSQL database via Spring Data JPA
- Auto-generated schema with Hibernate
- Post logging — every INSERT is recorded in a separate audit table
- Case-insensitive search by last name
- Static HTML frontend for testing endpoints

## Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Database:** PostgreSQL (Supabase)
- **ORM:** Spring Data JPA / Hibernate
- **Build Tool:** Maven

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/pojo` | Get all records |
| GET | `/pojo/{id}` | Get record by ID |
| GET | `/pojo/lastnocase/{lastname}` | Search by last name (case-insensitive) |
| POST | `/pojo` | Create a new record |
| POST | `/pojos` | Bulk create records |
| PUT | `/pojo` | Update an existing record |
| DELETE | `/pojo/{id}` | Delete a record by ID |
| GET | `/postlogs` | View audit log of all POST operations |

## Getting Started

### Prerequisites
- Java 17+
- Maven
- A Supabase account and project (PostgreSQL)

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/spring-boot-supabase-crud.git
   cd spring-boot-supabase-crud
   ```

2. Set up environment variables:
   ```bash
   cp .env.example .env
   ```
   Fill in your Supabase PostgreSQL connection URL in `.env`.

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Open your browser at `http://localhost:8080`

Use `generic-interface.html` to interact with the API via a simple browser UI.
