package com.markskroba.devconnectorspringboot.auth;

import com.markskroba.devconnectorspringboot.auth.jwt.JwtAuthService;
import com.markskroba.devconnectorspringboot.users.User;
import com.markskroba.devconnectorspringboot.users.UserRepository;
import de.bripkens.gravatar.DefaultImage;
import de.bripkens.gravatar.Gravatar;
import de.bripkens.gravatar.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtAuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (request.getName() == null) throw new IllegalArgumentException("Name is required");
        if (request.getEmail() == null) throw new IllegalArgumentException("Please include valid email");
        if (request.getPassword() == null) throw new IllegalArgumentException("Password is required");

        var avatar = new Gravatar()
                .setSize(200)
                .setRating(Rating.PARENTAL_GUIDANCE_SUGGESTED)
                .setStandardDefaultImage(DefaultImage.MONSTER)
                .getUrl(request.getEmail());

        var user = User
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .avatar(avatar)
                .password(encoder.encode(request.getPassword()))
                .date(new Date())
                .build();
        userRepository.save(user);
        var token = authService.generateToken(user);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse login(AuthRequest request) {
        if (request.getEmail() == null) throw new IllegalArgumentException("Please include valid email");
        if (request.getPassword() == null) throw new IllegalArgumentException("Password is required");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var token = authService.generateToken(user);
        return AuthResponse.builder().token(token).build();
    }
}
