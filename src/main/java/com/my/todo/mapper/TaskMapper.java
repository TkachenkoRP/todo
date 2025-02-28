package com.my.todo.mapper;

import com.my.todo.dto.TaskFullResponseDto;
import com.my.todo.dto.TaskRequestDto;
import com.my.todo.dto.TaskShortResponseDto;
import com.my.todo.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMap.class})
public interface TaskMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "author", ignore = true),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "comments", ignore = true)
    })
    void updateTask(Task sourseTask, @MappingTarget Task targetTask);

    @Mappings({
            @Mapping(target = "author", source = "authorId"),
            @Mapping(target = "performer", source = "performerId")
    })
    Task toEntity(TaskRequestDto request);

    @Mappings({
            @Mapping(target = "author", source = "request.authorId"),
            @Mapping(target = "performer", source = "request.performerId")
    })
    Task toEntity(Long id, TaskRequestDto request);

    TaskShortResponseDto toShortDto(Task entity);

    TaskFullResponseDto toFullDto(Task entity);
}
