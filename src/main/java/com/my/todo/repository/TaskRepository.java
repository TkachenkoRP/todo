package com.my.todo.repository;

import com.my.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAuthor_Id(Long id);

    List<Task> findByPerformer_Id(Long id);
}