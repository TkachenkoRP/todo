package com.my.todo.controller;

import com.my.todo.dto.ErrorResponseDto;
import com.my.todo.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponseDto notFound(EntityNotFoundException e) {
        log.error("Ошибка при попытке получить сущность", e);
        return new ErrorResponseDto(e.getLocalizedMessage());
    }

}
