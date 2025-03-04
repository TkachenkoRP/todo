package com.my.todo.security;

import com.my.todo.dto.RefreshTokenRequestDto;
import com.my.todo.dto.RefreshTokenResponseDto;
import com.my.todo.dto.UserLoginRequestDto;
import com.my.todo.dto.UserLoginResponseDto;
import com.my.todo.dto.UserRegistrationRequestDto;
import com.my.todo.exception.RefreshTokenException;
import com.my.todo.model.RefreshToken;
import com.my.todo.model.RoleType;
import com.my.todo.model.User;
import com.my.todo.repository.CommentRepository;
import com.my.todo.repository.TaskRepository;
import com.my.todo.repository.UserRepository;
import com.my.todo.security.jwt.JwtUtils;
import com.my.todo.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public UserLoginResponseDto authenticateUser(UserLoginRequestDto userLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginRequest.getEmail(),
                userLoginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return UserLoginResponseDto.builder()
                .accessToken(jwtUtils.generateJwtToken(userDetails))
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public void register(UserRegistrationRequestDto request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user.setRoles(Collections.singleton(RoleType.ROLE_USER));

        userRepository.save(user);
    }

    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByRefreshToken(requestRefreshToken)
                .map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getUserId)
                .map(userId -> {
                    User tokenOwner = userRepository.findById(userId).orElseThrow(() ->
                            new RefreshTokenException("Exception trying to get token for userId: " + userId));
                    String token = jwtUtils.generateTokenFromUserEmail(tokenOwner.getEmail());
                    return new RefreshTokenResponseDto(token, refreshTokenService.createRefreshToken(userId).getToken());
                }).orElseThrow(() -> new RefreshTokenException(requestRefreshToken, "Refresh token not found"));
    }

    public void logout() {
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentPrincipal instanceof AppUserDetails userDetails) {
            Long userId = userDetails.getId();

            refreshTokenService.deleteByUserId(userId);
        }
    }

    public boolean isTaskAuthor(Long taskId, Long userId) {
        return taskRepository.existsByIdAndAuthor_Id(taskId, userId);
    }

    public boolean isCommentAuthor(Long commentId, Long userId) {
        return commentRepository.existsByIdAndAuthor_Id(commentId, userId);
    }
}
