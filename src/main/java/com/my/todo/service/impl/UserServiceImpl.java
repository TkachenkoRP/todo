package com.my.todo.service.impl;

import com.my.todo.exception.EntityNotFoundException;
import com.my.todo.model.RoleType;
import com.my.todo.model.User;
import com.my.todo.repository.UserRepository;
import com.my.todo.security.AppUserDetails;
import com.my.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "User with id {0} not found", id
        )));
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setRoles(Set.of(RoleType.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        User existedUser = findById(user.getId());
        existedUser.patchFrom(user);
        return userRepository.save(existedUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "User with email {0} not found", email
        )));
    }

    @Override
    public User getCurrentUser() {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findById(userDetails.getId());
    }

    @Override
    @Transactional
    public User addAdminRole(Long id) {
        User user = findById(id);
        user.getRoles().add(RoleType.ROLE_ADMIN);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User removeAdminRole(Long id) {
        User user = findById(id);
        user.getRoles().remove(RoleType.ROLE_ADMIN);
        return userRepository.save(user);
    }
}
