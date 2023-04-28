package com.markskroba.devconnectorspringboot.profiles;

import java.util.List;

public interface ProfileService {
    Profile getMyProfile();

     List<Profile> getAllProfiles();
}
