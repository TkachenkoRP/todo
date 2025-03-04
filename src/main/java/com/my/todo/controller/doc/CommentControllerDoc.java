package com.my.todo.controller.doc;

import com.my.todo.dto.CommentFullResponseDto;
import com.my.todo.dto.CommentRequestDto;
import com.my.todo.dto.CommentShortResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CommentControllerDoc {
    @Operation(summary = "Получить все комментарии", description = "Возвращает список всех комментариев")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список комментариев успешно получен"),
    })
    List<CommentShortResponseDto> getAll();

    @Operation(summary = "Получить комментарий по ID", description = "Возвращает полный объект комментария по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно найден"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    CommentFullResponseDto getById(
            @Parameter(description = "ID комментария", required = true) @PathVariable Long id
    );

    @Operation(summary = "Создать комментарий", description = "Создает новый комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Комментарий успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    CommentFullResponseDto create(
            @Parameter(description = "Данные для создания комментария", required = true) @RequestBody CommentRequestDto request
    );

    @Operation(summary = "Обновить комментарий", description = "Частично обновляет комментарий по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    CommentFullResponseDto patch(
            @Parameter(description = "ID комментария", required = true) @PathVariable Long id,
            @Parameter(description = "Данные для обновления комментария", required = true) @RequestBody CommentRequestDto request
    );

    @Operation(summary = "Удалить комментарий", description = "Удаляет комментарий по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Комментарий успешно удален"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    void delete(
            @Parameter(description = "ID комментария", required = true) @PathVariable Long id
    );
}
