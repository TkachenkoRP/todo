package com.my.todo.service.impl;

import com.my.todo.dto.TaskFilter;
import com.my.todo.exception.EntityNotFoundException;
import com.my.todo.model.Task;
import com.my.todo.model.TaskPriority;
import com.my.todo.model.TaskStatus;
import com.my.todo.model.User;
import com.my.todo.repository.TaskRepository;
import com.my.todo.repository.specification.TaskSpecification;
import com.my.todo.service.TaskService;
import com.my.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Page<Task> findAll(Pageable pageable, TaskFilter filter) {
        return taskRepository.findAll(TaskSpecification.withFilter(filter), pageable);
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
        User currentUser = userService.getCurrentUser();
        task.setAuthor(currentUser);
        task.setStatus(TaskStatus.WAITING);
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task update(Task task) {
        Task existedTask = findById(task.getId());
        existedTask.patchFrom(task);
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

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByAuthorId(Long id) {
        return taskRepository.findByAuthor_Id(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByPerformerId(Long id) {
        return taskRepository.findByPerformer_Id(id);
    }
}
