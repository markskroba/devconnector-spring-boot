package com.markskroba.devconnectorspringboot.users;

import com.markskroba.devconnectorspringboot.auth.jwt.JwtAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtAuthService authService;
    @Override
    public User getUser() {
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        return user;
    }
}
