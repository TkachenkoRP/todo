package com.my.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequestDto {
    @NotBlank(message = "Укажите имя пользователя.")
    private String name;
    @NotBlank(message = "Укажите email пользователя.")
    private String email;
    @NotBlank(message = "Укажите пароль пользователя.")
    private String password;
}
