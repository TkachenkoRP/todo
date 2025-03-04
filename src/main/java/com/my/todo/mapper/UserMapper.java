package com.my.todo.mapper;

import com.my.todo.dto.UserFullResponseDto;
import com.my.todo.dto.UserRegistrationRequestDto;
import com.my.todo.dto.UserShortResponseDto;
import com.my.todo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserRegistrationRequestDto request);

    User toEntity(Long id, UserRegistrationRequestDto request);

    @Mapping(target = "countTasksCreated", expression = "java(entity.getAuthoredTasks().size())")
    @Mapping(target = "countTasksAssigned", expression = "java(entity.getAssignedTasks().size())")
    @Mapping(target = "countComments", expression = "java(entity.getComments().size())")
    UserShortResponseDto toShortDto(User entity);

    @Mapping(target = "tasksCreated", source = "authoredTasks")
    @Mapping(target = "tasksAssigned", source = "assignedTasks")
    UserFullResponseDto toFullDto(User entity);
}
