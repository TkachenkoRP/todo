package com.my.todo.controller;

import com.my.todo.annotation.CanWorkWithTask;
import com.my.todo.controller.doc.TaskControllerDoc;
import com.my.todo.dto.TaskFilter;
import com.my.todo.dto.TaskFullResponseDto;
import com.my.todo.dto.TaskRequestDto;
import com.my.todo.dto.TaskShortResponseDto;
import com.my.todo.mapper.TaskMapper;
import com.my.todo.model.Task;
import com.my.todo.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
public class TaskController implements TaskControllerDoc {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public Page<TaskShortResponseDto> getAll(@PageableDefault Pageable pageable, @Valid TaskFilter filter) {
        Page<Task> taskList = taskService.findAll(pageable, filter);
        List<TaskShortResponseDto> responseDtoList = taskList.stream().map(taskMapper::toShortDto).toList();
        return new PageImpl<>(responseDtoList, pageable, responseDtoList.size());
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
    @CanWorkWithTask
    public TaskFullResponseDto patch(@PathVariable Long id, @RequestBody @Valid TaskRequestDto request) {
        Task entity = taskMapper.toEntity(id, request);
        Task updated = taskService.update(entity);
        TaskFullResponseDto fullResponseDto = taskMapper.toFullDto(updated);
        log.debug("Patch task: {}", fullResponseDto);
        return fullResponseDto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.deleteById(id);
    }

    @PatchMapping("/status/waiting/{id}")
    public TaskFullResponseDto setWaitingStatus(@PathVariable Long id) {
        Task task = taskService.setTaskWaitStatus(id);
        TaskFullResponseDto fullResponseDto = taskMapper.toFullDto(task);
        log.debug("Set task waiting status: {}", fullResponseDto);
        return fullResponseDto;
    }

    @PatchMapping("/status/in-progress/{id}")
    public TaskFullResponseDto setInProgressStatus(@PathVariable Long id) {
        Task task = taskService.setTaskInProgressStatus(id);
        TaskFullResponseDto fullResponseDto = taskMapper.toFullDto(task);
        log.debug("Set task in-progress status: {}", fullResponseDto);
        return fullResponseDto;
    }

    @PatchMapping("/status/done/{id}")
    public TaskFullResponseDto setDoneStatus(@PathVariable Long id) {
        Task task = taskService.setTaskDoneStatus(id);
        TaskFullResponseDto fullResponseDto = taskMapper.toFullDto(task);
        log.debug("Set task done status: {}", fullResponseDto);
        return fullResponseDto;
    }

    @PatchMapping("/priority/low/{id}")
    public TaskFullResponseDto setLowPriority(@PathVariable Long id) {
        Task task = taskService.setTaskLowPriority(id);
        TaskFullResponseDto fullResponseDto = taskMapper.toFullDto(task);
        log.debug("Set task low priority: {}", fullResponseDto);
        return fullResponseDto;
    }

    @PatchMapping("/priority/medium/{id}")
    public TaskFullResponseDto setMediumPriority(@PathVariable Long id) {
        Task task = taskService.setTaskMediumPriority(id);
        TaskFullResponseDto fullResponseDto = taskMapper.toFullDto(task);
        log.debug("Set task medium priority: {}", fullResponseDto);
        return fullResponseDto;
    }

    @PatchMapping("/priority/high/{id}")
    public TaskFullResponseDto setHighPriority(@PathVariable Long id) {
        Task task = taskService.setTaskHighPriority(id);
        TaskFullResponseDto fullResponseDto = taskMapper.toFullDto(task);
        log.debug("Set task high priority: {}", fullResponseDto);
        return fullResponseDto;
    }

    @GetMapping("/author/{id}")
    public List<TaskShortResponseDto> getByAuthorId(@PathVariable Long id) {
        List<Task> byAuthorId = taskService.findByAuthorId(id);
        List<TaskShortResponseDto> responseDtoList = byAuthorId.stream().map(taskMapper::toShortDto).toList();
        log.debug("Get tasks by author id: {} - {}", id, responseDtoList);
        return responseDtoList;
    }

    @GetMapping("/performer/{id}")
    public List<TaskShortResponseDto> getByPerformerId(@PathVariable Long id) {
        List<Task> byPerformerId = taskService.findByPerformerId(id);
        List<TaskShortResponseDto> responseDtoList = byPerformerId.stream().map(taskMapper::toShortDto).toList();
        log.debug("Get tasks by performer id: {} - {}", id, responseDtoList);
        return responseDtoList;
    }
}
