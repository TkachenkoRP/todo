package com.my.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    @NotBlank(message = "Комментарий не содержит текста.")
    @NotNull(message = "Комментарий не содержит текста.")
    private String text;
    @NotNull(message = "Не указан автор комментария.")
    private Long authorId;
    @NotNull(message = "Не указана задача.")
    private Long taskId;
}
