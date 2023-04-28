package com.markskroba.devconnectorspringboot.profiles;

import com.markskroba.devconnectorspringboot.auth.jwt.JwtAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        return ResponseEntity.ok().body(profileService.getAllProfiles());
    }
    @GetMapping("/me")
    public ResponseEntity<Profile> getMyProfile() {
        return ResponseEntity.ok().body(profileService.getMyProfile());
    }

}
