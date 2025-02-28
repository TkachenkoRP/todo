package com.my.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentFullResponseDto {
    private Long id;
    private String text;
    private UserShortResponseDto author;
    private Instant createAt;
    private Instant updatedAt;
}
