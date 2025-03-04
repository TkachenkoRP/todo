package com.my.todo.controller.doc;

import com.my.todo.dto.UserFullResponseDto;
import com.my.todo.dto.UserRegistrationRequestDto;
import com.my.todo.dto.UserShortResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserControllerDoc {
    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен"),
    })
    List<UserShortResponseDto> getAll();

    @Operation(summary = "Получить пользователя по ID", description = "Возвращает полный объект пользователя по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    UserFullResponseDto getById(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id
    );

    @Operation(summary = "Создать пользователя", description = "Создает нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    UserFullResponseDto create(
            @Parameter(description = "Данные для регистрации пользователя", required = true) @RequestBody UserRegistrationRequestDto request
    );

    @Operation(summary = "Обновить пользователя", description = "Частично обновляет пользователя по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    UserFullResponseDto patch(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id,
            @Parameter(description = "Данные для обновления пользователя", required = true) @RequestBody UserRegistrationRequestDto request
    );

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    void delete(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id
    );

    @Operation(summary = "Добавить роль администратора", description = "Добавляет роль администратора пользователю по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Роль администратора успешно добавлена"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    UserFullResponseDto addAdminRole(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id
    );

    @Operation(summary = "Удалить роль администратора", description = "Удаляет роль администратора у пользователя по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Роль администратора успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    UserFullResponseDto removeAdminRole(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id
    );
}
