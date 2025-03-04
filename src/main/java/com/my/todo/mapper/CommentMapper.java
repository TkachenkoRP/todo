package com.my.todo.mapper;

import com.my.todo.dto.CommentFullResponseDto;
import com.my.todo.dto.CommentRequestDto;
import com.my.todo.dto.CommentShortResponseDto;
import com.my.todo.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMap.class, TaskMap.class})
public interface CommentMapper {

    @Mapping(target = "author", source = "authorId")
    @Mapping(target = "task", source = "taskId")
    Comment toEntity(CommentRequestDto request);


    @Mapping(target = "author", source = "request.authorId")
    @Mapping(target = "task", source = "request.taskId")
    Comment toEntity(Long id, CommentRequestDto request);

    CommentShortResponseDto toShortDto(Comment entity);

    CommentFullResponseDto toFullDto(Comment entity);
}
