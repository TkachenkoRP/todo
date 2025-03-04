package com.my.todo.controller.doc;

import com.my.todo.dto.TaskFilter;
import com.my.todo.dto.TaskFullResponseDto;
import com.my.todo.dto.TaskRequestDto;
import com.my.todo.dto.TaskShortResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TaskControllerDoc {
    @Operation(summary = "Получить все задачи", description = "Возвращает список всех задач с возможностью фильтрации и пагинации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен"),
    })
    Page<TaskShortResponseDto> getAll(
            @Parameter(description = "Параметры пагинации") @PageableDefault Pageable pageable,
            @Parameter(description = "Фильтр задач") @Valid TaskFilter filter
    );

    @Operation(summary = "Получить задачу по ID", description = "Возвращает полную информацию о задаче по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    TaskFullResponseDto getById(
            @Parameter(description = "ID задачи", required = true) @PathVariable Long id
    );

    @Operation(summary = "Создать задачу", description = "Создает новую задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    TaskFullResponseDto create(
            @Parameter(description = "Данные для создания задачи", required = true) @RequestBody @Valid TaskRequestDto request
    );

    @Operation(summary = "Обновить задачу", description = "Частично обновляет задачу по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    TaskFullResponseDto patch(
            @Parameter(description = "ID задачи", required = true) @PathVariable Long id,
            @Parameter(description = "Данные для обновления задачи", required = true) @RequestBody @Valid TaskRequestDto request
    );

    @Operation(summary = "Удалить задачу", description = "Удаляет задачу по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    void delete(
            @Parameter(description = "ID задачи", required = true) @PathVariable Long id
    );

    @Operation(summary = "Установить статус ожидания", description = "Устанавливает статус 'ожидания' для задачи по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус задачи успешно изменен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    TaskFullResponseDto setWaitingStatus(
            @Parameter(description = "ID задачи", required = true) @PathVariable Long id
    );

    @Operation(summary = "Установить статус в процессе", description = "Устанавливает статус 'в процессе' для задачи по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус задачи успешно изменен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    TaskFullResponseDto setInProgressStatus(
            @Parameter(description = "ID задачи", required = true) @PathVariable Long id
    );

    @Operation(summary = "Установить статус выполнено", description = "Устанавливает статус 'выполнено' для задачи по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус задачи успешно изменен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    TaskFullResponseDto setDoneStatus(
            @Parameter(description = "ID задачи", required = true) @PathVariable Long id
    );

    @Operation(summary = "Установить низкий приоритет", description = "Устанавливает низкий приоритет для задачи по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приоритет задачи успешно изменен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    TaskFullResponseDto setLowPriority(
            @Parameter(description = "ID задачи", required = true) @PathVariable Long id
    );

    @Operation(summary = "Установить средний приоритет", description = "Устанавливает средний приоритет для задачи по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приоритет задачи успешно изменен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    TaskFullResponseDto setMediumPriority(
            @Parameter(description = "ID задачи", required = true) @PathVariable Long id
    );

    @Operation(summary = "Установить высокий приоритет", description = "Устанавливает высокий приоритет для задачи по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приоритет задачи успешно изменен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    TaskFullResponseDto setHighPriority(
            @Parameter(description = "ID задачи", required = true) @PathVariable Long id
    );

    @Operation(summary = "Получить задачи по ID автора", description = "Возвращает список задач, созданных автором по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен"),
            @ApiResponse(responseCode = "404", description = "Автор не найден")
    })
    List<TaskShortResponseDto> getByAuthorId(
            @Parameter(description = "ID автора", required = true) @PathVariable Long id
    );

    @Operation(summary = "Получить задачи по ID исполнителя", description = "Возвращает список задач, назначенных исполнителю по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен"),
            @ApiResponse(responseCode = "404", description = "Исполнитель не найден")
    })
    List<TaskShortResponseDto> getByPerformerId(
            @Parameter(description = "ID исполнителя", required = true) @PathVariable Long id
    );
}
