package com.my.todo.mapper;

import com.my.todo.dto.UserFullResponseDto;
import com.my.todo.dto.UserRequest;
import com.my.todo.dto.UserShortResponseDto;
import com.my.todo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "authoredTasks", ignore = true),
            @Mapping(target = "assignedTasks", ignore = true),
            @Mapping(target = "comments", ignore = true)
    })
    void updateUser(User sourceUser, @MappingTarget User targetUser);

    User toEntity(UserRequest request);

    User toEntity(Long id, UserRequest request);

    @Mappings({
            @Mapping(target = "countTasksCreated", expression = "java(entity.getAuthoredTasks().size())"),
            @Mapping(target = "countTasksAssigned", expression = "java(entity.getAssignedTasks().size())"),
            @Mapping(target = "countComments", expression = "java(entity.getComments().size())")
    })
    UserShortResponseDto toShortDto(User entity);

    @Mappings({
            @Mapping(target = "tasksCreated", source = "authoredTasks"),
            @Mapping(target = "tasksAssigned", source = "assignedTasks")
    })
    UserFullResponseDto toFullDto(User entity);
}
