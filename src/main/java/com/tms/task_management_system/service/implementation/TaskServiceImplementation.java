package com.tms.task_management_system.service.implementation;

import com.tms.task_management_system.exception.InvalidSortParameterException;
import com.tms.task_management_system.exception.TaskNotFoundException;
import com.tms.task_management_system.model.Priority;
import com.tms.task_management_system.model.Status;
import com.tms.task_management_system.model.Task;
import com.tms.task_management_system.repository.TaskRepository;
import com.tms.task_management_system.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskServiceImplementation implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImplementation.class);
    private final TaskRepository repository;

    public TaskServiceImplementation(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task createTask(String title, String description, LocalDate dueDate, Priority priority, Status status) {
        try {
            logger.debug("Creating task with title: {}", title);
            Task task = new Task(title, description, dueDate, priority, status);
            repository.save(task);
            logger.info("Task created successfully with ID: {}", task.getId());
            return task;
        } catch (Exception e) {
            logger.error("Failed to create task: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating task", e);
        }
    }

    @Override
    public Optional<Task> updateTask(String id, String title, String description,
                                     LocalDate dueDate, Priority priority, Status status) {
        try {
            logger.debug("Attempting to update task with id: {}", id);

            // Ensure task exists
            if (repository.findById(id).isEmpty()) {
                throw new TaskNotFoundException("Task not found with ID: " + id);
            }

            Task updated = new Task( title, description, dueDate, priority, status);
            repository.save(updated);

            logger.info("Task with id {} updated successfully", id);
            return Optional.of(updated);

        } catch (TaskNotFoundException e) {
            logger.warn("Update failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while updating task with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Internal server error occurred while updating the task.");
        }
    }


    @Override
    public boolean deleteTask(String id) {
        try {
            logger.debug("Attempting to delete task with id: {}", id);

            return repository.findById(id)
                    .map(task -> {
                        repository.delete(id);
                        logger.info("Task with id {} deleted successfully", id);
                        return true;
                    })
                    .orElseThrow(() -> {
                        logger.warn("Task with id {} not found for deletion", id);
                        return new TaskNotFoundException("Task not found with ID: " + id);
                    });

        } catch (TaskNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while deleting task with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Internal server error occurred while deleting the task.");
        }
    }


    @Override
    public List<Task> listTasks(Optional<Status> status, Optional<Priority> priority,
                                Optional<LocalDate> fromDate, Optional<LocalDate> toDate,
                                Optional<String> sortBy) {
        try {
            logger.debug("Listing tasks with filters and optional sorting");

            Stream<Task> taskStream = repository.findAll().stream()
                    .filter(task -> status.map(s -> s.equals(task.getStatus())).orElse(true))
                    .filter(task -> priority.map(p -> p.equals(task.getPriority())).orElse(true))
                    .filter(task -> fromDate.map(date -> task.getDueDate() != null && !task.getDueDate().isBefore(date)).orElse(true))
                    .filter(task -> toDate.map(date -> task.getDueDate() != null && !task.getDueDate().isAfter(date)).orElse(true));

            if (sortBy.isPresent()) {
                String sortKey = sortBy.get().toLowerCase();
                taskStream = switch (sortKey) {
                    case "duedate" -> taskStream.sorted(
                            Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
                    );
                    case "priority" -> taskStream.sorted(
                            Comparator.comparing(Task::getPriority, Comparator.nullsLast(Comparator.reverseOrder()))
                    );
                    case "duedate_priority" -> taskStream.sorted(
                            Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
                                    .thenComparing(Task::getPriority, Comparator.nullsLast(Comparator.naturalOrder()))
                    );
                    default -> {
                        logger.warn("Invalid sortBy parameter received: {}", sortKey);
                        throw new InvalidSortParameterException(sortKey);
                    }
                };
            }

            List<Task> result = taskStream.collect(Collectors.toList());
            logger.info("Listed {} tasks successfully", result.size());
            return result;

        } catch (InvalidSortParameterException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Failed to list tasks: {}", e.getMessage(), e);
            throw new RuntimeException("Internal server error occurred while listing tasks.");
        }
    }



}
