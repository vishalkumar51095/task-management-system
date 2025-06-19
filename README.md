# 📝 Task Management System - Java Spring Boot

![Java](https://img.shields.io/badge/Java-17+-brightgreen?logo=java)  
![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.3.12-blue?logo=springboot)
![Swagger](https://img.shields.io/badge/API-Documented-orange?logo=swagger)
![JUnit](https://img.shields.io/badge/Tested-JUnit5-yellow)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen)

---

## 📌 Overview

This project is a modular and testable **Task Management System** built using **Java** and **Spring Boot**. It allows users to manage tasks (create, update, delete, list) with **in-memory** storage. It supports **REST APIs** and **Command-Line Interface (CLI)** for task manipulation, and includes proper *logging*, *exception handling*, and *unit testing*.
---

## 🚀 Features

- 🔗 REST API (Postman & Swagger support)
- 💻 CLI for terminal interaction
- 💾 In-memory storage (no DB needed)
- ✅ Unit tests using JUnit
- ⚠️ Global exception handling
- 📜 Proper structured logging
- ✨ Clean Code using Java 8+ features

---

## 🧩 Project Structure

```

src/
├── model/                 # Enums & Task entity
├── dto/                   # Task DTO
├── repository/            # TaskRepository interface & impl
├── service/               # Business logic
├── api/                   # REST Controller
├── cli/                   # CLI interface
├── exception/             # Custom exceptions and handler
└── test/                  # JUnit test classes
└── aop/                   # AOP classes

````

---

## 📦 Package Overview

| Package      | Class/File                           | Description                                                                                    |
| ------------ | ------------------------------------ | ---------------------------------------------------------------------------------------------- |
| `model`      | `Task.java`                          | The main entity containing fields like ID, title, description, due date, priority, and status. |
|              | `Priority.java`                      | Enum for task priority – LOW, MEDIUM, HIGH.                                                    |
|              | `Status.java`                        | Enum for task status – PENDING, IN_PROGRESS, COMPLETED.                                       |
| `dto`        | `TaskDto.java`                       | Data Transfer Object used to receive and return task data.                                     |
| `repository` | `TaskRepository.java`                | Interface defining repository operations.                                                      |
|              | `TaskRepositoryImpl.java`            | Concrete in-memory implementation of the repository.                                           |
| `service`    | `TaskService.java`                   | Interface for business logic.                                                                  |
|              | `TaskServiceImplementation.java`     | Implements business logic including validation and filters.                                    |
| `api`        | `TaskController.java`                | REST Controller exposing endpoints for CRUD operations and task listing.                       |
| `cli`        | `TaskManagerCLI.java`                | CLI runner allowing task operations from the terminal.                                         |
| `exception`  | `GlobalExceptionHandler.java`        | Centralized error handler for REST API.                                                        |
|              | `TaskNotFoundException.java`         | Custom exception thrown when task is not found.                                                |
|              | `InvalidSortParameterException.java` | Custom exception for invalid sorting.                                                          |
| `test`       | `TaskServiceImplementationTest.java` | Unit tests for task service logic.                                                             |
|              | `TaskManagerCLITest.java`            | Unit tests for CLI behavior (mocked I/O).                                                      |

---

## 🧠 Core Functionalities

### ✅ Create Task  
- `POST /tasks`  
- CLI: `create`  
![Create Task](screenshots/create_task_swagger.png)

---

### ✏️ Update Task  
- `PUT /tasks/{id}`  
- CLI: `update`  
![Update Task](screenshots/update_task.png)

---

### ❌ Delete Task  
- `DELETE /tasks/{id}`  
- CLI: `delete`  
![Delete Task](screenshots/delete_task.png)

---

### 📋 List Tasks  
- `GET /tasks?status=&priority=&fromDate=&toDate=&sortBy=`  
- CLI: `list`  
![List Tasks](screenshots/list_tasks.png)

---

## 📚 Tech Stack

- Java 17
- Spring Boot 3.3.12
- Maven
- JUnit 5
- Swagger UI (OpenAPI)
- SLF4J + Logback
- AOP 

---

## 🧪 Testing

```bash
mvn test
````

* `TaskServiceImplementationTest` ✅
* `TaskManagerCLITest` ✅

📸 ![Test Result](screenshots/test_results.png)

---

## 🔐 Error Handling

* Custom Exceptions:

  * `TaskNotFoundException`
  * `InvalidSortParameterException`
* Centralized via `GlobalExceptionHandler`
  📸 ![Error Handling](screenshots/exception_example.png)

---

## 🛠️ How to Run

```bash
git clone https://github.com/yourusername/task-management-system.git
cd task-management-system

# Build the project
mvn clean install

# Run the application
java -jar target/task-management-system.jar
```

🌐 Access Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔮 Future Enhancements

* 🧵 Async & Multithreading support
* 🐳 Dockerize the project
* ☁ Deploy to Render or Railway
* 🔐 OAuth2 / Okta Integration for Authentication
* 🌐 Add a Web Frontend (React/Vue)
* 🗃️ Use PostgreSQL/MySQL for persistence

---

## 📸 Screenshots

> Put the following images inside `screenshots/` folder in the root:

* `create_task_swagger.png`
* `update_task.png`
* `delete_task.png`
* `list_tasks.png`
* `swagger_ui.png`
* `cli_example.png`
* `exception_example.png`
* `unit_testing.png`
* `test_results.png`

---

## 🤝 Author

**Vishal Kumar**
🌐 [LinkedIn](https://linkedin.com/in/vishalkumar51095) | 📧 [vishalkumar51095@gmail.com](mailto:vishalkumar51095@gmail.com)

---

## 📜 License

This project is licensed under the MIT License.

```
