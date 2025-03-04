package com.my.todo.controller.doc;

import com.my.todo.dto.RefreshTokenRequestDto;
import com.my.todo.dto.RefreshTokenResponseDto;
import com.my.todo.dto.UserLoginRequestDto;
import com.my.todo.dto.UserLoginResponseDto;
import com.my.todo.dto.UserRegistrationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDoc {

    @Operation(summary = "Аутентификация пользователя", description = "Позволяет пользователю войти в систему")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная аутентификация"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })
    UserLoginResponseDto auth(
            @Parameter(description = "Данные для аутентификации пользователя")
            @RequestBody UserLoginRequestDto request
    );

    @Operation(summary = "Регистрация пользователя", description = "Позволяет зарегистрировать нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Email уже зарегистрирован")
    })
    void register(
            @Parameter(description = "Данные для регистрации пользователя")
            @RequestBody UserRegistrationRequestDto request
    );

    @Operation(summary = "Обновление токена", description = "Позволяет обновить токен доступа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    RefreshTokenResponseDto refreshToken(
            @Parameter(description = "Данные для обновления токена")
            @RequestBody RefreshTokenRequestDto request
    );

    @Operation(summary = "Выход пользователя", description = "Позволяет пользователю выйти из системы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешный выход"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
    })
    void logout(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserDetails userDetails
    );
}
