package com.tms.task_management_system;

import com.tms.task_management_system.model.Priority;
import com.tms.task_management_system.model.Status;
import com.tms.task_management_system.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest(properties = "spring.profiles.active=test")
class TaskManagementSystemApplicationTests {

	@Autowired
	private TaskService taskService;
	@Test
	void contextLoads() {
	}

	@Test
	void testAopLogging() {
		taskService.createTask("Test", "Description", LocalDate.now(), Priority.HIGH, Status.PENDING);

		try {
			taskService.deleteTask("edb18b22-ed1d-4fe3-8d23-e9dd9afb49b0");
		} catch (Exception ignored) {
		}
	}
}
