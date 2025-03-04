package com.my.todo.controller;

import com.my.todo.annotation.CanWorkWithComment;
import com.my.todo.controller.doc.CommentControllerDoc;
import com.my.todo.dto.CommentFullResponseDto;
import com.my.todo.dto.CommentRequestDto;
import com.my.todo.dto.CommentShortResponseDto;
import com.my.todo.mapper.CommentMapper;
import com.my.todo.model.Comment;
import com.my.todo.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController implements CommentControllerDoc {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping
    public List<CommentShortResponseDto> getAll() {
        List<Comment> comments = commentService.findAll();
        List<CommentShortResponseDto> responseDtoList = comments.stream().map(commentMapper::toShortDto).toList();
        log.debug("Get all comments: {}", responseDtoList);
        return responseDtoList;
    }

    @GetMapping("/{id}")
    public CommentFullResponseDto getById(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        CommentFullResponseDto fullResponseDto = commentMapper.toFullDto(comment);
        log.debug("Get comment by id: {} - {}", id, fullResponseDto);
        return fullResponseDto;
    }

    @PostMapping
    public CommentFullResponseDto create(@RequestBody @Valid CommentRequestDto request) {
        Comment entity = commentMapper.toEntity(request);
        Comment saved = commentService.save(entity);
        CommentFullResponseDto fullResponseDto = commentMapper.toFullDto(saved);
        log.debug("Create comment: {}", fullResponseDto);
        return fullResponseDto;
    }

    @PatchMapping("/{id}")
    @CanWorkWithComment
    public CommentFullResponseDto patch(@PathVariable Long id, @RequestBody @Valid CommentRequestDto request) {
        Comment entity = commentMapper.toEntity(id, request);
        Comment updated = commentService.update(entity);
        CommentFullResponseDto fullResponseDto = commentMapper.toFullDto(updated);
        log.debug("Patch comment: {}", fullResponseDto);
        return fullResponseDto;
    }

    @DeleteMapping("/{id}")
    @CanWorkWithComment
    public void delete(@PathVariable Long id) {
        commentService.deleteById(id);
    }
}
