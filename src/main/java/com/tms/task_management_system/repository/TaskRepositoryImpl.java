package com.tms.task_management_system.repository;
import com.tms.task_management_system.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskRepositoryImpl implements TaskRepository {
    private final Map<String, Task> taskMap = new ConcurrentHashMap<>();

    @Override
    public void save(Task task) {
        taskMap.put(task.getId(), task);
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(taskMap.get(id));
    }

    @Override
    public void delete(String id) {
        taskMap.remove(id);
    }

    @Override
    public Collection<Task> findAll() {
        return taskMap.values();
    }
}