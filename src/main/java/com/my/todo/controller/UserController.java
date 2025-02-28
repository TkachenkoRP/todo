package com.my.todo.controller;

import com.my.todo.dto.UserFullResponseDto;
import com.my.todo.dto.UserRequest;
import com.my.todo.dto.UserShortResponseDto;
import com.my.todo.mapper.UserMapper;
import com.my.todo.model.User;
import com.my.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserShortResponseDto> getAll() {
        List<User> users = userService.findAll();
        List<UserShortResponseDto> responseDtoList = users.stream().map(userMapper::toShortDto).toList();
        log.debug("Get all users: {}", responseDtoList);
        return responseDtoList;
    }

    @GetMapping("/{id}")
    public UserFullResponseDto getById(@PathVariable Long id) {
        User user = userService.findById(id);
        UserFullResponseDto fullResponseDto = userMapper.toFullDto(user);
        log.debug("Get user by id: {} - {}", id, fullResponseDto);
        return fullResponseDto;
    }

    @PostMapping
    public UserFullResponseDto create(@RequestBody UserRequest request) {
        User entity = userMapper.toEntity(request);
        User saved = userService.save(entity);
        UserFullResponseDto fullResponseDto = userMapper.toFullDto(saved);
        log.debug("Create user: {}", fullResponseDto);
        return fullResponseDto;
    }

    @PatchMapping("/{id}")
    public UserFullResponseDto path(@PathVariable Long id, @RequestBody UserRequest request) {
        User entity = userMapper.toEntity(id, request);
        User updated = userService.update(entity);
        UserFullResponseDto fullResponseDto = userMapper.toFullDto(updated);
        log.debug("Patch user: {}", fullResponseDto);
        return fullResponseDto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
