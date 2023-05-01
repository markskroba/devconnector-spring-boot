package com.markskroba.devconnectorspringboot.auth;

import com.markskroba.devconnectorspringboot.auth.jwt.JwtAuthService;
import com.markskroba.devconnectorspringboot.posts.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<ResponseMessage> handleNotFound(Exception e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        if (e.getClass() == IllegalArgumentException.class) httpStatus = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new ResponseMessage(e.getMessage()), httpStatus);
    }
}
