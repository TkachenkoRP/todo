package com.my.todo.security.jwt;

import com.my.todo.security.AppUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.tokenExpiration}")
    private Duration tokenExpiration;

    public String generateJwtToken(AppUserDetails userDetails) {
        return generateTokenFromUserEmail(userDetails.getEmail());
    }

    public String generateTokenFromUserEmail(String email) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpiration.toMillis()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUserEmail(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String authToken) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SecurityException e) {
            log.error("Неверная подпись токена: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Некорректный токен: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Срок действия токена истёк: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Неподдерживаемый токен: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Тело токена пустое: {}", e.getMessage());
        }
        return false;
    }

}
