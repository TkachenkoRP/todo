package com.my.todo.mapper;

import com.my.todo.model.User;
import com.my.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMap {
    private final UserService userService;

    public User fromId(Long id) {
        return id != null ? userService.findById(id) : null;
    }
}
