package com.my.todo.service;

import com.my.todo.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    Comment findById(Long id);

    Comment save(Comment comment);

    Comment update(Long id, Comment comment);

    void deleteById(Long id);
}
