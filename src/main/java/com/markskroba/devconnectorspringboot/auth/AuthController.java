package com.markskroba.devconnectorspringboot.auth;

import com.markskroba.devconnectorspringboot.users.User;
import com.markskroba.devconnectorspringboot.users.UserService;
import com.markskroba.devconnectorspringboot.posts.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping()
    public ResponseEntity<User> getUser(@RequestHeader("x-auth-token") String token) {
        return ResponseEntity.ok(userService.getUser());
    }

    @PostMapping()
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
