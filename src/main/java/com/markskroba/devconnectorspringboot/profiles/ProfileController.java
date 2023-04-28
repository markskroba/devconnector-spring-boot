package com.markskroba.devconnectorspringboot.profiles;

import com.markskroba.devconnectorspringboot.auth.jwt.JwtAuthService;
import com.markskroba.devconnectorspringboot.posts.ResponseMessage;
import com.markskroba.devconnectorspringboot.profiles.dto.CreateProfileDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    public ResponseEntity<Profile> createProfile(@RequestBody CreateProfileDto dto) {
        return ResponseEntity.ok().body(profileService.createProfile(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Profile> getUserProfile(@PathVariable("userId") String userId) {
       return ResponseEntity.ok().body(profileService.getUserProfile(userId));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ResponseMessage> deleteUserProfile(@PathVariable("userId") String userId) {
        return ResponseEntity.ok().body(profileService.deleteUserProfile(userId));
    }
}
