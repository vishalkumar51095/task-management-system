package com.tms.task_management_system.aop;


import com.tms.task_management_system.model.Priority;
import com.tms.task_management_system.model.Status;
import com.tms.task_management_system.model.Task;
import com.tms.task_management_system.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LoggingAspectTest {
//
//    @Autowired
//    private TaskService taskService;
//
//    @Test
//    void shouldLogAopMessagesForCreateTask() {
//        // Start capturing logs for the AOP class
//        LogCaptor logCaptor = LogCaptor.forClass(LoggingAspect.class);
//        logCaptor.setLogLevelToInfo();
//
//        // Call a method that is intercepted by AOP
//        Task task = taskService.createTask("AOP Test", "Log test", LocalDate.now(), Priority.LOW, Status.PENDING);
//
//        // Get captured logs
//        List<String> logs = logCaptor.getInfoLogs();
//
//        // Assert that AOP entry and exit logs are captured
//        assertTrue(logs.stream().anyMatch(log -> log.contains("➡️ Entering")));
//        assertTrue(logs.stream().anyMatch(log -> log.contains("✅ Completed")));
//        assertTrue(logs.stream().anyMatch(log -> log.contains("createTask")));
//        assertTrue(logs.stream().anyMatch(log -> log.contains(task.getId())));
//    }
}
