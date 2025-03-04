package com.my.todo.dto;

import com.my.todo.model.TaskPriority;
import com.my.todo.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskFullResponseDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private UserShortResponseDto author;
    private UserShortResponseDto performer;
    private Instant createAt;
    private Instant updatedAt;
    private List<CommentFullResponseDto> comments;
}
