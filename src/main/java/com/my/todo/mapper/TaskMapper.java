package com.my.todo.mapper;

import com.my.todo.dto.TaskFullResponseDto;
import com.my.todo.dto.TaskRequestDto;
import com.my.todo.dto.TaskShortResponseDto;
import com.my.todo.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMap.class})
public interface TaskMapper {

    @Mapping(target = "performer", source = "performerId")
    Task toEntity(TaskRequestDto request);

    @Mapping(target = "performer", source = "request.performerId")
    Task toEntity(Long id, TaskRequestDto request);

    TaskShortResponseDto toShortDto(Task entity);

    TaskFullResponseDto toFullDto(Task entity);
}
