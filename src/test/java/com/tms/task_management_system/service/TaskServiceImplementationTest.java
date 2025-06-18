package com.tms.task_management_system.service;

import com.tms.task_management_system.exception.TaskNotFoundException;
import com.tms.task_management_system.model.Priority;
import com.tms.task_management_system.model.Status;
import com.tms.task_management_system.model.Task;
import com.tms.task_management_system.repository.TaskRepository;
import com.tms.task_management_system.service.implementation.TaskServiceImplementation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplementationTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskServiceImplementation service;

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImplementationTest.class);


    private Task createSampleTask(String title, String description, Priority priority, Status status) {
        return new Task(title, description, LocalDate.now(), priority, status);
    }


    @Test
    @DisplayName("Should throw exception when creating task with empty title")
    void givenEmptyTitle_whenCreateTask_thenThrowException() {
        logger.info("Running test: givenEmptyTitle_whenCreateTask_thenThrowException");

        assertThrows(IllegalArgumentException.class, () -> {
            logger.debug("Attempting to create task with empty title...");
            new Task("", "desc", LocalDate.now(), Priority.LOW, Status.PENDING);
        });

        logger.info("Test passed: IllegalArgumentException was correctly thrown for empty title");
    }


    @Test
    @DisplayName("Should create a task successfully")
    void givenValidDetails_whenCreateTask_thenTaskIsCreated() {
        Task sampleTask = createSampleTask("Sample Title", "Sample Desc", Priority.MEDIUM, Status.PENDING);
        doNothing().when(repository).save(any(Task.class));

        Task result = service.createTask(
                sampleTask.getTitle(),
                sampleTask.getDescription(),
                sampleTask.getDueDate(),
                sampleTask.getPriority(),
                sampleTask.getStatus()
        );

        assertNotNull(result);
        assertEquals(sampleTask.getTitle(), result.getTitle());
        assertEquals(sampleTask.getPriority(), result.getPriority());
        verify(repository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should update task successfully when task exists")
    void givenValidId_whenUpdateTask_thenTaskUpdated() {
        Task existingTask = createSampleTask("Old Title", "Old Desc", Priority.HIGH, Status.PENDING);
        String taskId = existingTask.getId();

        when(repository.findById(taskId)).thenReturn(Optional.of(existingTask));
        doNothing().when(repository).save(any(Task.class));

        Optional<Task> updated = service.updateTask(
                taskId,
                "Updated Title",
                "Updated Desc",
                LocalDate.now().plusDays(2),
                Priority.LOW,
                Status.COMPLETED
        );

        assertTrue(updated.isPresent());
        Task updatedTask = updated.get();
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("Updated Desc", updatedTask.getDescription());
        assertEquals(Priority.LOW, updatedTask.getPriority());
        assertEquals(Status.COMPLETED, updatedTask.getStatus());

        verify(repository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent task")
    void givenInvalidId_whenUpdateTask_thenThrowException() {
        String invalidId = "non-existent-id";
        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            service.updateTask(invalidId, "Title", "Desc", LocalDate.now(), Priority.MEDIUM, Status.PENDING);
        });

        verify(repository, never()).save(any(Task.class));
        logger.info("Test passed: TaskNotFoundException was correctly thrown.");

    }

    @Test
    @DisplayName("Should delete task successfully when task exists")
    void givenValidId_whenDeleteTask_thenSuccess() {
        Task taskToDelete = createSampleTask("To Delete", "Description", Priority.LOW, Status.PENDING);
        String taskId = taskToDelete.getId();

        when(repository.findById(taskId)).thenReturn(Optional.of(taskToDelete));
        doNothing().when(repository).delete(taskId);

        boolean result = service.deleteTask(taskId);
        assertTrue(result);

        verify(repository, times(1)).delete(taskId);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent task")
    void givenInvalidId_whenDeleteTask_thenThrowException() {
        String invalidId = "unknown-id";

        logger.info("Running test: givenInvalidId_whenDeleteTask_thenThrowException");
        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            logger.debug("Attempting to delete task with ID: {}", invalidId);
            service.deleteTask(invalidId);
        });

        verify(repository, never()).delete(anyString());
        logger.info("Test passed: TaskNotFoundException was correctly thrown for ID '{}'", invalidId);
    }
}
