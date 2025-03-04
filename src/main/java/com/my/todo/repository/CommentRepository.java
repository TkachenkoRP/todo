package com.my.todo.repository;

import com.my.todo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    boolean existsByIdAndAuthor_Id(Long id, Long authorId);
}
