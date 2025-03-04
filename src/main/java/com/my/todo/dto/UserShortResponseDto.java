package com.my.todo.dto;

import com.my.todo.model.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<RoleType> roles;
}
