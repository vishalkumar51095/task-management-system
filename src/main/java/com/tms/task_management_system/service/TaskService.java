package com.tms.task_management_system.service;

import com.tms.task_management_system.model.Priority;
import com.tms.task_management_system.model.Status;
import com.tms.task_management_system.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public interface TaskService {
    Task createTask(String title, String description, LocalDate dueDate, Priority priority, Status status);

    Optional<Task> updateTask(String id, String title, String description, LocalDate dueDate, Priority priority, Status status);

    boolean deleteTask(String id);

    List<Task> listTasks(Optional<Status> status, Optional<Priority> priority,
                         Optional<LocalDate> fromDate, Optional<LocalDate> toDate,
                         Optional<String> sortBy);
}
