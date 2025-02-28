package com.my.todo.service.impl;

import com.my.todo.exception.EntityNotFoundException;
import com.my.todo.mapper.TaskMapper;
import com.my.todo.model.Task;
import com.my.todo.model.TaskPriority;
import com.my.todo.model.TaskStatus;
import com.my.todo.repository.TaskRepository;
import com.my.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Task with id {0} not found", id
        )));
    }

    @Override
    @Transactional
    public Task save(Task task) {
        task.setStatus(TaskStatus.WAITING);
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task update(Task task) {
        Task existedTask = findById(task.getId());
        taskMapper.updateTask(task, existedTask);
        return taskRepository.save(existedTask);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Task setTaskWaitStatus(Long id) {
        return setTaskStatus(id, TaskStatus.WAITING);
    }

    @Override
    @Transactional
    public Task setTaskInProgressStatus(Long id) {
        return setTaskStatus(id, TaskStatus.IN_PROGRESS);
    }

    @Override
    @Transactional
    public Task setTaskDoneStatus(Long id) {
        return setTaskStatus(id, TaskStatus.DONE);
    }

    private Task setTaskStatus(Long id, TaskStatus status) {
        Task task = findById(id);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task setTaskLowPriority(Long id) {
        return setTaskPriority(id, TaskPriority.LOW);
    }

    @Override
    @Transactional
    public Task setTaskMediumPriority(Long id) {
        return setTaskPriority(id, TaskPriority.MEDIUM);
    }

    @Override
    @Transactional
    public Task setTaskHighPriority(Long id) {
        return setTaskPriority(id, TaskPriority.HIGH);
    }

    private Task setTaskPriority(Long id, TaskPriority priority) {
        Task task = findById(id);
        task.setPriority(priority);
        return taskRepository.save(task);
    }
}
