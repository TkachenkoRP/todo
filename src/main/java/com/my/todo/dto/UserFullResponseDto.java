package com.my.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFullResponseDto {
    private Long id;
    private String name;
    private String email;
    private List<TaskShortResponseDto> tasksCreated;
    private List<TaskShortResponseDto> tasksAssigned;
    private List<CommentShortResponseDto> comments;
}
