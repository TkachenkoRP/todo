package com.my.todo.controller;

import com.my.todo.controller.doc.AuthControllerDoc;
import com.my.todo.dto.RefreshTokenRequestDto;
import com.my.todo.dto.RefreshTokenResponseDto;
import com.my.todo.dto.UserLoginRequestDto;
import com.my.todo.dto.UserLoginResponseDto;
import com.my.todo.dto.UserRegistrationRequestDto;
import com.my.todo.exception.AlreadyExitsException;
import com.my.todo.repository.UserRepository;
import com.my.todo.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @PostMapping("/signin")
    public UserLoginResponseDto auth(@RequestBody UserLoginRequestDto request) {
        return securityService.authenticateUser(request);
    }

    @PostMapping("/register")
    public void register(@RequestBody UserRegistrationRequestDto request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new AlreadyExitsException("Email уже зарегистрирован");
        }

        securityService.register(request);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponseDto refreshToken(@RequestBody RefreshTokenRequestDto request) {
        return securityService.refreshToken(request);
    }

    @PatchMapping("/logout")
    public void logout(@AuthenticationPrincipal UserDetails userDetails) {
        securityService.logout();
    }
}
