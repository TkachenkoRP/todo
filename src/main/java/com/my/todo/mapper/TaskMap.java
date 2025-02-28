package com.my.todo.mapper;

import com.my.todo.model.Task;
import com.my.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMap {
    private final TaskService taskService;

    public Task fromId(Long id) {
        return id != null ? taskService.findById(id) : null;
    }
}
