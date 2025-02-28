package com.my.todo.controller;

import com.my.todo.model.Task;
import com.my.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<Task> getAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping
    public Task create(@RequestBody Task request) {
        return taskService.save(request);
    }

    @PatchMapping("/{id}")
    public Task patch(@PathVariable Long id, @RequestBody Task request) {
        return taskService.update(id, request);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
