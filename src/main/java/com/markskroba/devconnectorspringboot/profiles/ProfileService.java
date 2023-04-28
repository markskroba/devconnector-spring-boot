package com.markskroba.devconnectorspringboot.profiles;

import com.markskroba.devconnectorspringboot.profiles.dto.CreateProfileDto;

import java.util.List;

public interface ProfileService {
    Profile getMyProfile();

    List<Profile> getAllProfiles();

    Profile createProfile(CreateProfileDto dto);
}
