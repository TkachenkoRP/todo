package com.my.todo.service;

import com.my.todo.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAll();

    Task findById(Long id);

    Task save(Task task);

    Task update(Task task);

    void deleteById(Long id);

    Task setTaskWaitStatus(Long id);

    Task setTaskInProgressStatus(Long id);

    Task setTaskDoneStatus(Long id);

    Task setTaskLowPriority(Long id);

    Task setTaskMediumPriority(Long id);

    Task setTaskHighPriority(Long id);

    List<Task> findByAuthorId(Long id);

    List<Task> findByPerformerId(Long id);
}
