package com.markskroba.devconnectorspringboot.profiles;

import com.markskroba.devconnectorspringboot.posts.ResponseMessage;
import com.markskroba.devconnectorspringboot.profiles.dto.CreateProfileDto;

import java.util.List;

public interface ProfileService {
    ProfileWithUserData getMyProfileWithUserData();

    List<ProfileWithUserData> getAllProfiles();

    ProfileWithUserData getUserProfile(String userId);

    Profile createProfile(CreateProfileDto dto);


    ResponseMessage deleteUserProfile();

    Profile addEducation(EducationData dto);

    Profile addExperience(ExperienceData dto);

    Profile deleteEducation(String id);

    Profile deleteExperience(String id);
}
