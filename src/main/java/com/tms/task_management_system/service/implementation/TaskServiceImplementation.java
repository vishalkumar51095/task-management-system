package com.tms.task_management_system.service.implementation;

import com.tms.task_management_system.model.Priority;
import com.tms.task_management_system.model.Status;
import com.tms.task_management_system.model.Task;
import com.tms.task_management_system.repository.TaskRepository;
import com.tms.task_management_system.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            return task;
        } catch (Exception e) {
            logger.error("Failed to create task: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating task", e);
        }
    }

    @Override
    public Optional<Task> updateTask(String id, String title, String description, LocalDate dueDate, Priority priority, Status status) {
        try {
            logger.debug("Updating task with id: {}", id);
            Optional<Task> taskOpt = repository.findById(id);
            taskOpt.ifPresent(task -> {
                task.setTitle(title);
                task.setDescription(description);
                task.setDueDate(dueDate);
                task.setPriority(priority);
                task.setStatus(status);
            });
            return taskOpt;
        } catch (Exception e) {
            logger.error("Error updating task with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error updating task", e);
        }
    }

    @Override
    public boolean deleteTask(String id) {
        logger.debug("Deleting task with id: {}", id);

        return repository.findById(id).map(task -> {
            repository.delete(id);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Task> listTasks(Optional<Status> status, Optional<Priority> priority, Optional<LocalDate> fromDate, Optional<LocalDate> toDate) {
        logger.debug("Listing tasks with filters");

        return repository.findAll().stream()
                .filter(task -> !status.isPresent() || task.getStatus() == status.get())
                .filter(task -> !priority.isPresent() || task.getPriority() == priority.get())
                .filter(task -> !fromDate.isPresent() || (task.getDueDate() != null && !task.getDueDate().isBefore(fromDate.get())))
                .filter(task -> !toDate.isPresent() || (task.getDueDate() != null && !task.getDueDate().isAfter(toDate.get())))
                .collect(Collectors.toList());
    }
}
