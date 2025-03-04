package com.my.todo.service.impl;

import com.my.todo.dto.TaskFilter;
import com.my.todo.exception.EntityNotFoundException;
import com.my.todo.model.Task;
import com.my.todo.model.TaskPriority;
import com.my.todo.model.TaskStatus;
import com.my.todo.model.User;
import com.my.todo.repository.TaskRepository;
import com.my.todo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        task = new Task();
        task.setId(1L);
    }

    @Test
    void findAll_ShouldReturnPageOfTasks() {
        Pageable pageable = mock(Pageable.class);
        TaskFilter filter = mock(TaskFilter.class);
        Page<Task> taskPage = mock(Page.class);

        when(taskRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(taskPage);

        Page<Task> result = taskService.findAll(pageable, filter);

        assertNotNull(result);
        verify(taskRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findById_ShouldReturnTask_WhenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.findById(1L);

        assertEquals(task, result);
    }

    @Test
    void findById_ShouldThrowException_WhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.findById(1L));
    }

    @Test
    void save_ShouldSaveTask_WhenValidTask() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.save(task);

        assertEquals(task, result);
        assertEquals(user, task.getAuthor());
        assertEquals(TaskStatus.WAITING, task.getStatus());
    }

    @Test
    void update_ShouldUpdateTask_WhenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.update(task);

        assertEquals(task, result);
        verify(taskRepository).save(task);
    }

    @Test
    void update_ShouldThrowException_WhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.update(task));
    }

    @Test
    void deleteById_ShouldDeleteTask_WhenTaskExists() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteById(1L);

        verify(taskRepository).deleteById(1L);
    }

    @Test
    void setTaskWaitStatus_ShouldSetStatusToWaiting() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.setTaskWaitStatus(1L);

        assertEquals(TaskStatus.WAITING, result.getStatus());
    }

    @Test
    void setTaskInProgressStatus_ShouldSetStatusToInProgress() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.setTaskInProgressStatus(1L);

        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
    }

    @Test
    void setTaskDoneStatus_ShouldSetStatusToDone() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.setTaskDoneStatus(1L);

        assertEquals(TaskStatus.DONE, result.getStatus());
    }

    @Test
    void setTaskLowPriority_ShouldSetPriorityToLow() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.setTaskLowPriority(1L);

        assertEquals(TaskPriority.LOW, result.getPriority());
    }

    @Test
    void setTaskMediumPriority_ShouldSetPriorityToMedium() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.setTaskMediumPriority(1L);

        assertEquals(TaskPriority.MEDIUM, result.getPriority());
    }

    @Test
    void setTaskHighPriority_ShouldSetPriorityToHigh() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.setTaskHighPriority(1L);

        assertEquals(TaskPriority.HIGH, result.getPriority());
    }

    @Test
    void findByAuthorId_ShouldReturnListOfTasks() {
        when(taskRepository.findByAuthor_Id(1L)).thenReturn(List.of(task));

        List<Task> result = taskService.findByAuthorId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findByPerformerId_ShouldReturnListOfTasks() {
        when(taskRepository.findByPerformer_Id(1L)).thenReturn(List.of(task));

        List<Task> result = taskService.findByPerformerId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
