package com.my.todo.service.impl;

import com.my.todo.exception.EntityNotFoundException;
import com.my.todo.mapper.UserMapper;
import com.my.todo.model.User;
import com.my.todo.repository.UserRepository;
import com.my.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "User with id {0} not found", id
        )));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User existedUser = findById(id);
        userMapper.updateUser(user, existedUser);
        return userRepository.save(existedUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
