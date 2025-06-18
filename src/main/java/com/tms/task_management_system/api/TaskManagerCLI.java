package com.tms.task_management_system.api;


import com.tms.task_management_system.model.Priority;
import com.tms.task_management_system.model.Status;
import com.tms.task_management_system.model.Task;
import com.tms.task_management_system.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TaskManagerCLI {
    private final TaskService service;
    private final Scanner scanner = new Scanner(System.in);

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
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void update() {
        System.out.print("Task ID: ");
        String id = scanner.nextLine();
        service.updateTask(id, prompt("Title"), prompt("Description"),
                        parseDate(prompt("Due Date (yyyy-mm-dd)")), parseEnum(Priority.class, prompt("Priority")),
                        parseEnum(Status.class, prompt("Status")))
                .ifPresentOrElse(task -> System.out.println("Updated: " + task),
                        () -> System.out.println("Task not found"));
    }

    private void delete() {
        System.out.print("Task ID: ");
        String id = scanner.nextLine();
        boolean deleted = service.deleteTask(id);
        System.out.println(deleted ? "Deleted." : "Task not found.");
    }

    private void list() {
        System.out.print("Filter Status (blank for none): ");
        Optional<Status> status = parseOptionalEnum(Status.class, scanner.nextLine());
        System.out.print("Filter Priority (blank for none): ");
        Optional<Priority> priority = parseOptionalEnum(Priority.class, scanner.nextLine());
        System.out.print("From Due Date (yyyy-mm-dd or blank): ");
        Optional<LocalDate> fromDate = parseOptionalDate(scanner.nextLine());
        System.out.print("To Due Date (yyyy-mm-dd or blank): ");
        Optional<LocalDate> toDate = parseOptionalDate(scanner.nextLine());

        List<Task> tasks = service.listTasks(status, priority, fromDate, toDate);
        tasks.forEach(System.out::println);
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

