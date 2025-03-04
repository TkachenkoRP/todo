package com.my.todo.service.impl;

import com.my.todo.exception.EntityNotFoundException;
import com.my.todo.model.Comment;
import com.my.todo.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void testFindAll() {
        when(commentRepository.findAll()).thenReturn(Collections.singletonList(new Comment()));

        List<Comment> comments = commentService.findAll();
        assertNotNull(comments);
        assertEquals(1, comments.size());
    }

    @Test
    void testFindById_Success() {
        Comment comment = new Comment();
        comment.setId(1L);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comment foundComment = commentService.findById(1L);
        assertNotNull(foundComment);
        assertEquals(1L, foundComment.getId());
    }

    @Test
    void testFindById_NotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            commentService.findById(1L);
        });
        assertEquals("Comment with id 1 not found", exception.getMessage());
    }

    @Test
    void testSave() {
        Comment comment = new Comment();
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment savedComment = commentService.save(comment);
        assertNotNull(savedComment);
    }

    @Test
    void testUpdate_Success() {
        Comment existingComment = new Comment();
        existingComment.setId(1L);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(existingComment));

        Comment updatedComment = new Comment();
        updatedComment.setId(1L);
        when(commentRepository.save(existingComment)).thenReturn(existingComment);

        Comment result = commentService.update(updatedComment);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdate_NotFound() {
        Comment updatedComment = new Comment();
        updatedComment.setId(1L);
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            commentService.update(updatedComment);
        });
        assertEquals("Comment with id 1 not found", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        doNothing().when(commentRepository).deleteById(1L);
        assertDoesNotThrow(() -> commentService.deleteById(1L));
    }
}
