package com.my.todo.mapper;

import com.my.todo.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "task", ignore = true),
            @Mapping(target = "author", ignore = true)
    })
    void updateComment(Comment sourseCcomment, @MappingTarget Comment targetComment);
}
