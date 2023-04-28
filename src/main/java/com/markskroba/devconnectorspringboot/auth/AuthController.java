package com.markskroba.devconnectorspringboot.auth;

import com.markskroba.devconnectorspringboot.auth.jwt.JwtAuthService;
import com.markskroba.devconnectorspringboot.users.User;
import com.markskroba.devconnectorspringboot.users.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
