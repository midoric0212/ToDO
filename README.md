# ToDO Application

> A todo management system built with **Spring Boot 3** and **Java 17**, featuring a clean separation between frontend and backend with full CRUD operations.

## Features

- ✅ **Tech Stack**: Java 17 + Spring Boot 3.2.1 + PostgreSQL
- ✅ **REST API**: Full CRUD operations with JSON responses
- ✅ **Frontend**: Vanilla JavaScript with responsive design
- ✅ **Database**: JPA/Hibernate with PostgreSQL
- ✅ **Containerized**: Docker PostgreSQL deployment

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.2.1
- Spring Data JPA
- Hibernate 6.4.1
- PostgreSQL 15
- Maven 3.9.11

### Frontend
- HTML5 + CSS3 + Vanilla JavaScript
- Fetch API for HTTP communication
- DOM manipulation and event handling

## Project Structure

```
ToDO/
├── pom.xml                           # Maven configuration
├── src/
│   ├── main/java/com/example/ToDO/
│   │   ├── ToDoApplication.java      # Main Spring Boot application
│   │   ├── controller/
│   │   │   └── TodoController.java   # REST API endpoints
│   │   ├── service/
│   │   │   └── TodoService.java      # Business logic
│   │   ├── repository/
│   │   │   └── TodoRepository.java   # Data access layer
│   │   └── model/
│   │       └── Todo.java             # Entity class
│   ├── main/resources/
│   │   ├── application.properties    # App configuration
│   │   └── static/                   # Frontend files
│   │       ├── ToDO.html
│   │       ├── ToDO.css
│   │       └── ToDO.js
│   └── test/java/
│       └── ToDoApplicationTests.java
└── target/                           # Build output
```

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+** (or use included Maven Wrapper)
- **Docker** for PostgreSQL database

## Quick Start

### 1. Start PostgreSQL Database
```bash
docker run --name pg-todo \
  -e POSTGRES_DB=pg-todo \
  -e POSTGRES_USER=testuser \
  -e POSTGRES_PASSWORD=rootpass \
  -p 5432:5432 \
  -d postgres:15
```

### 2. Clone and Build
```bash
git clone <repository-url>
cd ToDO
./mvnw clean compile
```

### 3. Run Application
```bash
# Command line
./mvnw spring-boot:run

# Or run in background
nohup ./mvnw spring-boot:run > app.log 2>&1 &
```

### 4. Access Application
- **Frontend**: http://localhost:8080/ToDO.html
- **API**: http://localhost:8080/api/todos

## 🔌 API Endpoints

| Method | Path | Description | Request Body |
|--------|------|-------------|--------------|
| `GET` | `/api/todos` | Get all todos | - |
| `POST` | `/api/todos` | Create new todo | `{"content": "Learn Spring Boot"}` |
| `PUT` | `/api/todos/{id}` | Update todo | `{"title": "Updated", "done": true}` |
| `DELETE` | `/api/todos/{id}` | Delete todo | - |
