package com.my.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserShortResponseDto {
    private Long id;
    private String name;
    private String email;
    private int countTasksCreated;
    private int countTasksAssigned;
    private int countComments;
}
