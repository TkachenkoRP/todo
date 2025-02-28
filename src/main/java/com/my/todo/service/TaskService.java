package com.my.todo.service;

import com.my.todo.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAll();

    Task findById(Long id);

    Task save(Task task);

    Task update(Long id, Task task);

    void deleteById(Long id);
}
