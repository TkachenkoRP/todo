package com.my.todo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {
    @Email(message = "Не верно указан email")
    private String email;
    @NotBlank(message = "Укажите пароль пользователя.")
    private String password;
}
