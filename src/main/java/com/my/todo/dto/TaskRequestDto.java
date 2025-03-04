package com.my.todo.dto;

import com.my.todo.model.TaskPriority;
import com.my.todo.valid.ValueOfEnum;
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
public class TaskRequestDto {
    @NotBlank(message = "Укажите заголовок задачи.")
    private String title;
    @NotBlank(message = "Укажите описание задачи.")
    private String description;
    @NotNull(message = "Укажите приоритет задачи.")
    @ValueOfEnum(enumClass = TaskPriority.class)
    private String priority;
    @NotNull(message = "Укажите исполнителя.")
    private Long performerId;
}
