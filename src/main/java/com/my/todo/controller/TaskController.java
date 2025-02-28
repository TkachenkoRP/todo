package com.my.todo.controller;

import com.my.todo.dto.TaskFullResponseDto;
import com.my.todo.dto.TaskRequestDto;
import com.my.todo.dto.TaskShortResponseDto;
import com.my.todo.mapper.TaskMapper;
import com.my.todo.model.Task;
import com.my.todo.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public List<TaskShortResponseDto> getAll() {
        List<Task> taskList = taskService.findAll();
        List<TaskShortResponseDto> responseDtoList = taskList.stream().map(taskMapper::toShortDto).toList();
        log.debug("Get all task: {}", responseDtoList);
        return responseDtoList;
    }

    @GetMapping("/{id}")
    public TaskFullResponseDto getById(@PathVariable Long id) {
        Task task = taskService.findById(id);
        TaskFullResponseDto fullResponseDto = taskMapper.toFullDto(task);
        log.debug("Get comment by id: {} - {}", id, fullResponseDto);
        return fullResponseDto;
    }

    @PostMapping
    public TaskFullResponseDto create(@RequestBody @Valid TaskRequestDto request) {
        Task entity = taskMapper.toEntity(request);
        Task saved = taskService.save(entity);
        TaskFullResponseDto fullResponseDto = taskMapper.toFullDto(saved);
        log.debug("Create task: {}", fullResponseDto);
        return fullResponseDto;
    }

    @PatchMapping("/{id}")
    public TaskFullResponseDto patch(@PathVariable Long id, @RequestBody @Valid TaskRequestDto request) {
        Task entity = taskMapper.toEntity(id, request);
        Task updated = taskService.update(entity);
        TaskFullResponseDto fullResponseDto = taskMapper.toFullDto(updated);
        log.debug("Patch task: {}", fullResponseDto);
        return fullResponseDto;
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
