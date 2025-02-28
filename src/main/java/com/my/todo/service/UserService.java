package com.my.todo.service;

import com.my.todo.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User update(Long id, User user);

    void deleteById(Long id);
}
