package com.my.todo.dto;

import com.my.todo.model.TaskPriority;
import com.my.todo.model.TaskStatus;
import com.my.todo.valid.ValueOfEnum;

public record TaskFilter(Long id,
                         String title,
                         @ValueOfEnum(enumClass = TaskStatus.class) String status,
                         @ValueOfEnum(enumClass = TaskPriority.class) String priority,
                         Long authorId,
                         Long performerId) {
}