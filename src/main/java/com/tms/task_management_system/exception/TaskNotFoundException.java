package com.tms.task_management_system.exception;


public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String id) {
        super("Task not found with ID: " + id);
    }
}
