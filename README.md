📘 Task Management System – Java Coding Assignment

📄 Overview

This project is a modular and testable Task Management System built using Java. It allows users to manage tasks (create, update, delete, list) with in-memory storage. It supports REST APIs and Command-Line Interface (CLI) for task manipulation, and includes proper logging, exception handling, and unit testing.

📆 Project Structure

Package

Class/File

Description

model

Task.java

The main entity containing fields like ID, title, description, due date, priority, and status.



Priority.java

Enum for task priority – LOW, MEDIUM, HIGH.



Status.java

Enum for task status – PENDING, IN_PROGRESS, COMPLETED.

dto

TaskDto.java

Data Transfer Object used to receive and return task data.

repository

TaskRepository.java

Interface defining repository operations.



TaskRepositoryImpl.java

Concrete in-memory implementation of the repository.

service

TaskService.java

Interface for business logic.



TaskServiceImplementation.java

Implements business logic including validation and filters.

api

TaskController.java

REST Controller exposing endpoints for CRUD operations and task listing.

cli

TaskManagerCLI.java

CLI runner allowing task operations from the terminal.

exception

GlobalExceptionHandler.java

Centralized error handler for REST API.



TaskNotFoundException.java

Custom exception thrown when task is not found.



InvalidSortParameterException.java

Custom exception for invalid sorting.

test

TaskServiceImplementationTest.java

Unit tests for task service logic.



TaskManagerCLITest.java

Unit tests for CLI behavior (mocked I/O).

✅ Core Features

🔹 1. Create Task

Endpoint: POST /tasks

CLI: create

Fields: ID (auto), title, description, due date, priority, status

📸 Screenshot: 

🔹 2. Update Task

Endpoint: PUT /tasks/{id}

CLI: update

Allows modification of title, description, due date, priority, status

📸 Screenshot: 

🔹 3. Delete Task

Endpoint: DELETE /tasks/{id}

CLI: delete

📸 Screenshot: 

🔹 4. List Tasks

Endpoint: GET /tasks

CLI: list

Supports filtering by:

Status

Priority

Due date range

Sorting by priority or dueDate

📸 Screenshot: 

🛠️ Additional Features

Swagger UI for easy REST API testing📸 

Command-Line Interface📸 

Exception Handling using @ControllerAdvice📸 

JUnit Testing with AssertJ/Mockito📸 

Logging with SLF4J + LogbackLogs actions and errors meaningfully.

🤪 How to Run

▶ Run the Application

Clone the repository:

git clone https://github.com/yourusername/task-management-system.git
cd task-management-system

Build the project:

mvn clean install

Run using CLI:

java -jar target/task-management-system.jar

Access Swagger UI at:

http://localhost:8080/swagger-ui/index.html

🧪 Testing

Run all tests using:

mvn test

TaskServiceImplementationTest: Tests business logic including validation, filtering.

TaskManagerCLITest: Tests CLI commands using simulated user input.

📸 

🛠️ Technologies Used

Java 11+

Maven

Spring Boot

JUnit 5

Swagger (Springfox/OpenAPI)

SLF4J + Logback

Optional: Mockito for mocking in tests

🔮 Future Enhancements

🧵 Multithreading (Async task execution)

🐳 Dockerize for container deployment

☁ Deploy on Render.com or any cloud platform

🔐 Integrate Okta or Spring Security for user authentication

🎨 Frontend (React/Vue/Angular) for web UI

📃 Database support (PostgreSQL/MySQL) for persistence

📷 Screenshots

Add the following screenshots in a folder named screenshots/ in your repo and reference them in README.

create_task_swagger.png

update_task.png

delete_task.png

list_tasks.png

swagger_ui.png

cli_example.png

exception_example.png

unit_testing.png

test_results.png

