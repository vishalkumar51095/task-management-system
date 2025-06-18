package com.tms.task_management_system.dto;

import com.tms.task_management_system.model.Priority;
import com.tms.task_management_system.model.Status;

import java.time.LocalDate;

public class TaskDto {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private Status status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

