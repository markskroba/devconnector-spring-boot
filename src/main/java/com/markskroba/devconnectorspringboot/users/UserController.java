package com.markskroba.devconnectorspringboot.users;

import com.markskroba.devconnectorspringboot.auth.AuthResponse;
import com.markskroba.devconnectorspringboot.auth.AuthService;
import com.markskroba.devconnectorspringboot.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<AuthResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
