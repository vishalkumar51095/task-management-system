package com.tms.task_management_system;

import com.tms.task_management_system.api.TaskManagerCLI;
import com.tms.task_management_system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
public class TaskManagementSystemApplication implements CommandLineRunner {

	private final TaskService taskService;

	TaskManagementSystemApplication(TaskService taskService) {
		this.taskService = taskService;
	}

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 👇 Skip CLI if active profile is "test"
		if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			return;
		}
		TaskManagerCLI cli = new TaskManagerCLI(taskService);
		cli.run();
	}

}
