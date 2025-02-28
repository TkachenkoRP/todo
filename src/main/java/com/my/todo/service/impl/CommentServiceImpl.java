package com.my.todo.service.impl;

import com.my.todo.exception.EntityNotFoundException;
import com.my.todo.mapper.CommentMapper;
import com.my.todo.model.Comment;
import com.my.todo.repository.CommentRepository;
import com.my.todo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Comment with id {0} not found", id
                )));
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Long id, Comment comment) {
        Comment existedComment = findById(id);
        commentMapper.updateComment(comment, existedComment);
        return commentRepository.save(existedComment);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
