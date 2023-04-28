package com.markskroba.devconnectorspringboot.profiles;

import com.markskroba.devconnectorspringboot.auth.jwt.JwtAuthService;
import com.markskroba.devconnectorspringboot.posts.ResponseMessage;
import com.markskroba.devconnectorspringboot.profiles.dto.CreateProfileDto;
import com.markskroba.devconnectorspringboot.users.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final JwtAuthService authService;
    private final MongoOperations mongoOperations;

    @Override
    public Profile getMyProfile() {
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        String id = user.get_id();
        return this.getUserProfile(id);
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Profile createProfile(CreateProfileDto dto) {
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        String id = user.get_id();

        List<String> skills = Arrays.stream(dto.getSkills().split(", ")).toList();
        SocialsData socials = SocialsData.builder()
                .youtube(dto.getYoutube())
                .twitter(dto.getTwitter())
                .facebook(dto.getFacebook())
                .linkedin(dto.getLinkedin())
                .instagram(dto.getInstagram())
                .build();

        Profile p = Profile.builder()
                .user(new ObjectId(id))
                .company(dto.getCompany())
                .website(dto.getWebsite())
                .location(dto.getLocation())
                .bio(dto.getBio())
                .status(dto.getStatus())
                .githubuser(dto.getGithubusername())
                .skills(skills)
                .socials(socials)
                .build()
                ;

        profileRepository.save(p);
        return p;
    }

    @Override
    public Profile getUserProfile(String userId) {
        ObjectId id = new ObjectId(userId);
        Profile p = mongoOperations.findOne(Query.query(Criteria.where("user").is(id)), Profile.class);
        if (p == null) throw new NoSuchElementException("No profile for this user");
        return p;
    }

    @Override
    public ResponseMessage deleteUserProfile() {
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        String id = user.get_id();
        Profile p = this.getUserProfile(id);
        profileRepository.delete(p);
        return new ResponseMessage("User removed");
    }
}
