package com.my.todo.service;

import com.my.todo.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User update(User user);

    void deleteById(Long id);

    User findByEmail(String email);

    User getCurrentUser();

    User addAdminRole(Long id);

    User removeAdminRole(Long id);
}
