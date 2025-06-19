package com.tms.task_management_system.api;


import com.tms.task_management_system.model.Priority;
import com.tms.task_management_system.model.Status;
import com.tms.task_management_system.model.Task;
import com.tms.task_management_system.service.TaskService;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TaskManagerCLITest {

    private TaskService mockService;

    @BeforeEach
    void setUp() {
        mockService = mock(TaskService.class);
    }

    @Test
    void testCreateTaskSuccess() {
        // Sample user input for create() in the required order (with newlines)
        String input = String.join("\n",
                "Test Title",
                "Test Description",
                "2025-06-30",
                "HIGH",
                "PENDING"
        ) + "\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Task fakeTask = new Task("Test Title", "Test Description", LocalDate.parse("2025-06-30"), Priority.HIGH, Status.PENDING);

        when(mockService.createTask(anyString(), anyString(), any(), any(), any()))
                .thenReturn(fakeTask);

        TaskManagerCLI cli = new TaskManagerCLI(mockService);

        // Use reflection to invoke private method 'create' directly
        Assertions.assertDoesNotThrow(() -> {
            var createMethod = TaskManagerCLI.class.getDeclaredMethod("create");
            createMethod.setAccessible(true);
            createMethod.invoke(cli);
        });

        // Verify the service was called once
        verify(mockService, times(1)).createTask(
                eq("Test Title"),
                eq("Test Description"),
                eq(LocalDate.parse("2025-06-30")),
                eq(Priority.HIGH),
                eq(Status.PENDING)
        );
    }
}

