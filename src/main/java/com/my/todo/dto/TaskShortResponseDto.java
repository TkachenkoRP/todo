package com.my.todo.dto;

import com.my.todo.model.TaskPriority;
import com.my.todo.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskShortResponseDto {
    private Long id;
    private String title;
    private TaskStatus status;
    private TaskPriority priority;
    private Instant createAt;
}
