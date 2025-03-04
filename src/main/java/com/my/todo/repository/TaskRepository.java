package com.my.todo.repository;

import com.my.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    List<Task> findByAuthor_Id(Long id);

    List<Task> findByPerformer_Id(Long id);

    boolean existsByIdAndAuthor_Id(Long id, Long authorId);
}