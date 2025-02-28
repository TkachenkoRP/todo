package com.my.todo.service.impl;

import com.my.todo.exception.EntityNotFoundException;
import com.my.todo.mapper.TaskMapper;
import com.my.todo.model.Task;
import com.my.todo.repository.TaskRepository;
import com.my.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Task with id {0} not found", id
        )));
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task update(Long id, Task task) {
        Task existedTask = findById(id);
        taskMapper.updateTask(task, existedTask);
        return taskRepository.save(existedTask);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
