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
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(TaskManagerCLI.class);

    public TaskManagerCLI(TaskService service) {
        this.service = service;
    }

    public void run() {
        while (true) {
            System.out.println("\nTask Manager Options:\n1. Create\n2. Update\n3. Delete\n4. List\n5. Exit");
            switch (scanner.nextLine().trim()) {
                case "1": create(); break;
                case "2": update(); break;
                case "3": delete(); break;
                case "4": list(); break;
                case "5": return;
                default: System.out.println("Invalid input.");
            }
        }
    }

    private void create() {
        try {
            logger.info("Creating a new task");

            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Description: ");
            String desc = scanner.nextLine();
            System.out.print("Due Date (yyyy-mm-dd or blank): ");
            String dateStr = scanner.nextLine();
            LocalDate date = dateStr.isEmpty() ? null : LocalDate.parse(dateStr);
            System.out.print("Priority (LOW, MEDIUM, HIGH): ");
            Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Status (PENDING, IN_PROGRESS, COMPLETED): ");
            Status status = Status.valueOf(scanner.nextLine().toUpperCase());

            Task task = service.createTask(title, desc, date, priority, status);
            System.out.println("Task Created: " + task);
            logger.info("Task created with ID: {}", task.getId());
        } catch (Exception e) {
            logger.error("Error while creating task", e);
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void update() {
        logger.info("Updating a task");

        System.out.print("Task ID: ");
        String id = scanner.nextLine();

        Optional<Task> updatedTask = service.updateTask(id,
                prompt("Title"), prompt("Description"),
                parseDate(prompt("Due Date (yyyy-mm-dd)")),
                parseEnum(Priority.class, prompt("Priority")),
                parseEnum(Status.class, prompt("Status"))
        );

        if (updatedTask.isPresent()) {
            System.out.println("Updated: " + updatedTask.get());
            logger.info("Task updated successfully with ID: {}", id);
        } else {
            System.out.println("Task not found");
            logger.warn("Attempted to update non-existing task with ID: {}", id);
        }
    }


    private void delete() {
        logger.info("Deleting a task");

        System.out.print("Task ID: ");
        String id = scanner.nextLine();

        boolean deleted = service.deleteTask(id);
        if (deleted) {
            System.out.println("Deleted.");
            logger.info("Task deleted successfully with ID: {}", id);
        } else {
            logger.warn("Attempted to delete non-existing task with ID: {}", id);
        }
    }

    private void list() {
        logger.info("Starting task list operation");

        System.out.print("Filter Status (blank for none): ");
        Optional<Status> status = parseOptionalEnum(Status.class, scanner.nextLine());

        System.out.print("Filter Priority (blank for none): ");
        Optional<Priority> priority = parseOptionalEnum(Priority.class, scanner.nextLine());

        System.out.print("From Due Date (yyyy-mm-dd or blank): ");
        Optional<LocalDate> fromDate = parseOptionalDate(scanner.nextLine());

        System.out.print("To Due Date (yyyy-mm-dd or blank): ");
        Optional<LocalDate> toDate = parseOptionalDate(scanner.nextLine());

        System.out.print("Sort By (blank, duedate, priority, duedate_priority): ");
        String sortInput = scanner.nextLine().trim();
        Optional<String> sortBy = sortInput.isEmpty() ? Optional.empty() : Optional.of(sortInput.toLowerCase());

        logger.debug("Filters - Status: {}, Priority: {}, From: {}, To: {}, SortBy: {}",
                status.orElse(null), priority.orElse(null), fromDate.orElse(null), toDate.orElse(null), sortBy.orElse("none"));

        List<Task> tasks = service.listTasks(status, priority, fromDate, toDate, sortBy);

        if (tasks.isEmpty()) {
            logger.info("No tasks found with the given filters.");
        } else {
            tasks.forEach(System.out::println);
            logger.debug("Listed tasks: {}", tasks.size());
        }


        logger.info("Task list operation completed. Found {} task(s).", tasks.size());
    }


    private String prompt(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine();
    }

    private LocalDate parseDate(String input) {
        return input.isEmpty() ? null : LocalDate.parse(input);
    }

    private <T extends Enum<T>> T parseEnum(Class<T> clazz, String input) {
        return Enum.valueOf(clazz, input.toUpperCase());
    }

    private <T extends Enum<T>> Optional<T> parseOptionalEnum(Class<T> clazz, String input) {
        return input.isEmpty() ? Optional.empty() : Optional.of(parseEnum(clazz, input));
    }

    private Optional<LocalDate> parseOptionalDate(String input) {
        return input.isEmpty() ? Optional.empty() : Optional.of(LocalDate.parse(input));
    }
}

