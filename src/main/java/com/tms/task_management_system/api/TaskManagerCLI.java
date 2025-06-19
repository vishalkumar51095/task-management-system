package com.tms.task_management_system.api;

import com.tms.task_management_system.model.Priority;
import com.tms.task_management_system.model.Status;
import com.tms.task_management_system.model.Task;
import com.tms.task_management_system.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TaskManagerCLI {

    private final TaskService service;
    private final Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(TaskManagerCLI.class);

    public TaskManagerCLI(TaskService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        logger.info("Task Manager CLI started.");
        while (true) {
            try {
                System.out.println("\nTask Manager Options:\n1. Create\n2. Update\n3. Delete\n4. List\n5. Exit");
                switch (scanner.nextLine().trim()) {
                    case "1": create(); break;
                    case "2": update(); break;
                    case "3": delete(); break;
                    case "4": list(); break;
                    case "5":
                        logger.info("Task Manager CLI exited.");
                        return;
                    default:
                        logger.warn("Invalid menu option selected.");
                }
            } catch (Exception e) {
                logger.error("Unexpected error in main loop", e);
            }
        }
    }

    private void create() {
        try {
            logger.info("Creating a new task...");

            String title = prompt("Title");
            String desc = prompt("Description");
            LocalDate dueDate = parseOptionalDate(prompt("Due Date (yyyy-mm-dd or blank)")).orElse(null);
            Priority priority = parseEnum(Priority.class, prompt("Priority (LOW, MEDIUM, HIGH)"));
            Status status = parseEnum(Status.class, prompt("Status (PENDING, IN_PROGRESS, COMPLETED)"));

            Task task = service.createTask(title, desc, dueDate, priority, status);
            logger.info("Task created successfully with ID: {}", task.getId());

        } catch (IllegalArgumentException e) {
            logger.warn("Invalid input provided while creating task: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to create task", e);
        }
    }

    private void update() {
        try {
            logger.info("Updating a task...");

            String id = prompt("Task ID");

            Optional<Task> updatedTask = service.updateTask(id,
                    prompt("Title"), prompt("Description"),
                    parseOptionalDate(prompt("Due Date (yyyy-mm-dd or blank)")).orElse(null),
                    parseOptionalEnum(Priority.class, prompt("Priority (LOW, MEDIUM, HIGH)")).orElse(null),
                    parseOptionalEnum(Status.class, prompt("Status (PENDING, IN_PROGRESS, COMPLETED)")).orElse(null)
            );

            if (updatedTask.isPresent()) {
                logger.info("Task updated with ID: {}", id);
            } else {
                logger.warn("Task not found with ID: {}", id);
            }

        } catch (IllegalArgumentException e) {
            logger.warn("Invalid input provided while updating task: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to update task", e);
        }
    }

    private void delete() {
        try {
            logger.info("Deleting a task...");

            String id = prompt("Task ID");
            boolean deleted = service.deleteTask(id);

            if (deleted) {
                logger.info("Task deleted with ID: {}", id);
            } else {
                logger.warn("Delete failed. No task with ID: {}", id);
            }

        } catch (Exception e) {
            logger.error("Failed to delete task", e);
        }
    }

    private void list() {
        try {
            logger.info("Listing tasks...");

            Optional<Status> status = parseOptionalEnum(Status.class, prompt("Filter Status (blank for none)"));
            Optional<Priority> priority = parseOptionalEnum(Priority.class, prompt("Filter Priority (blank for none)"));
            Optional<LocalDate> fromDate = parseOptionalDate(prompt("From Due Date (yyyy-mm-dd or blank)"));
            Optional<LocalDate> toDate = parseOptionalDate(prompt("To Due Date (yyyy-mm-dd or blank)"));

            String sortByInput = prompt("Sort By (blank, duedate, priority, duedate_priority)");
            Optional<String> sortBy = sortByInput.isBlank() ? Optional.empty() : Optional.of(sortByInput.trim().toLowerCase());

            List<Task> tasks = service.listTasks(status, priority, fromDate, toDate, sortBy);

            if (tasks.isEmpty()) {
                logger.info("No tasks found.");
                System.out.println("⚠ No tasks found.");
            } else {
                logger.info("Found {} task(s)", tasks.size());
                tasks.forEach(System.out::println);
            }

        } catch (Exception e) {
            logger.error("Failed to list tasks", e);
        }
    }

    private String prompt(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine().trim();
    }

    private LocalDate parseDate(String input) {
        return LocalDate.parse(input.trim());
    }

    private Optional<LocalDate> parseOptionalDate(String input) {
        try {
            return input.isBlank() ? Optional.empty() : Optional.of(LocalDate.parse(input.trim()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd.");
        }
    }

    private <T extends Enum<T>> T parseEnum(Class<T> clazz, String input) {
        try {
            return Enum.valueOf(clazz, input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid " + clazz.getSimpleName() + " value.");
        }
    }

    private <T extends Enum<T>> Optional<T> parseOptionalEnum(Class<T> clazz, String input) {
        return input.isBlank() ? Optional.empty() : Optional.of(parseEnum(clazz, input));
    }
}
