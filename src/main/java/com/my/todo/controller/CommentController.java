package com.my.todo.controller;

import com.my.todo.model.Comment;
import com.my.todo.service.CommentService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<Comment> getAll() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public Comment getById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @PostMapping
    public Comment create(@RequestBody Comment request) {
        return commentService.save(request);
    }

    @PatchMapping("/{id}")
    public Comment patch(@PathVariable Long id, @RequestBody Comment request) {
        return commentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commentService.deleteById(id);
    }
}
