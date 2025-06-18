package com.tms.task_management_system.controller;

import com.tms.task_management_system.dto.TaskDto;
import com.tms.task_management_system.model.Priority;
import com.tms.task_management_system.model.Status;
import com.tms.task_management_system.model.Task;
import com.tms.task_management_system.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    // CREATE TASK
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody TaskDto taskDto) {
        logger.info("Creating task: {}", taskDto.getTitle());
        Task task = taskService.createTask(
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getDueDate(),
                taskDto.getPriority(),
                taskDto.getStatus()
        );
        return ResponseEntity.ok(task);
    }

    // UPDATE TASK
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable String id, @RequestBody TaskDto taskDto) {
        logger.info("Updating task with id: {}", id);
        return taskService.updateTask(
                        id,
                        taskDto.getTitle(),
                        taskDto.getDescription(),
                        taskDto.getDueDate(),
                        taskDto.getPriority(),
                        taskDto.getStatus()
                ).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE TASK
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        logger.info("Deleting task with id: {}", id);
        boolean deleted = taskService.deleteTask(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // LIST TASKS WITH FILTERING
    @GetMapping
    public ResponseEntity<List<Task>> listTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        logger.info("Listing tasks with filters - status: {}, priority: {}, from: {}, to: {}", status, priority, fromDate, toDate);
        List<Task> tasks = taskService.listTasks(
                Optional.ofNullable(status),
                Optional.ofNullable(priority),
                Optional.ofNullable(fromDate),
                Optional.ofNullable(toDate)
        );
        return ResponseEntity.ok(tasks);
    }
}
