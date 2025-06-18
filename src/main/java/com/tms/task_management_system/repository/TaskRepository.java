package com.tms.task_management_system.repository;

import com.tms.task_management_system.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepository {
    void save(Task task);
    Optional<Task> findById(String id);
    void delete(String id);
    Collection<Task> findAll();
}