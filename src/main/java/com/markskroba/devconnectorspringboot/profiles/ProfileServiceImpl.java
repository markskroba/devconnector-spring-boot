package com.markskroba.devconnectorspringboot.profiles;

import com.markskroba.devconnectorspringboot.auth.jwt.JwtAuthService;
import com.markskroba.devconnectorspringboot.posts.ResponseMessage;
import com.markskroba.devconnectorspringboot.profiles.dto.CreateProfileDto;
import com.markskroba.devconnectorspringboot.users.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
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
    private final MongoTemplate mongoTemplate;

    public ProfileWithUserData getMyProfileWithUserData() {
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        String id = user.get_id();
        return this.getUserProfile(id);
    }

    public Profile getMyProfile() {
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        System.out.println("User: " + user.toString());
        String id = user.get_id();
        System.out.println("ID: " + id);
        // temporary fix
        String profileID = this.getMyProfileWithUserData().get_id();
        return profileRepository.findById(profileID).orElseThrow(() -> new NoSuchElementException("No profile for this user"));
    }

    @Override
    public List<ProfileWithUserData> getAllProfiles() {
        LookupOperation lookup = LookupOperation.newLookup()
                .from("users")
                .localField("user")
                .foreignField("_id")
                .as("user");
        Aggregation aggregation = Aggregation.newAggregation(lookup, Aggregation.unwind("$user"));
        List<ProfileWithUserData> result = mongoTemplate.aggregate(aggregation, "profiles", ProfileWithUserData.class).getMappedResults();
        return result;
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

        if (dto.getStatus() == null) throw new IllegalArgumentException("Status is required");
        if (dto.getSkills() == null) throw new IllegalArgumentException("Skills are required");

        Profile p = Profile.builder()
                .user(id)
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
    public ProfileWithUserData getUserProfile(String userId) {
        var allProfiles = this.getAllProfiles().stream().filter(p -> userId.equals(p.getUser().get_id())).toList();
        if (allProfiles.size() == 0) throw new NoSuchElementException("No profile for this user");
        return allProfiles.get(0);
    }

    @Override
    public ResponseMessage deleteUserProfile() {
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        String id = user.get_id();
        Profile p = this.getUsersProfile(id);
        profileRepository.delete(p);
        return new ResponseMessage("User removed");
    }

    @Override
    public Profile addEducation(EducationData dto) {
        if (dto.getSchool() == null) throw new IllegalArgumentException("School is required");
        if (dto.getDegree() == null) throw new IllegalArgumentException("Degree is required");
        if (dto.getFieldofstudy() == null) throw new IllegalArgumentException("Field of Study is required");
        if (dto.getFrom() == null) throw new IllegalArgumentException("From date is required");

        Profile p = this.getMyProfile();
       var education = p.getEducation();
       if (education == null) education = new ArrayList<>();
       EducationData newEducation = EducationData.builder()
               ._id(new ObjectId().toString())
               .school(dto.getSchool())
               .degree(dto.getDegree())
               .fieldofstudy(dto.getFieldofstudy())
               .from(dto.getFrom())
               .from(dto.getFrom())
               .current(dto.getCurrent())
               .description(dto.getDescription())
               .build();
       education.add(newEducation);
       p.setEducation(education);
       profileRepository.save(p);
       return p;
    }

    @Override
    public Profile addExperience(ExperienceData dto) {
        if (dto.getTitle() == null) throw new IllegalArgumentException("Title is required");
        if (dto.getCompany() == null) throw new IllegalArgumentException("Company is required");
        if (dto.getFrom() == null) throw new IllegalArgumentException("From date is required");

        Profile p = this.getMyProfile();
        var experience = p.getExperience();
        if (experience == null) experience = new ArrayList<>();
        ExperienceData newExperience = ExperienceData.builder()
                ._id(new ObjectId().toString())
                .title(dto.getTitle())
                .company(dto.getCompany())
                .location(dto.getLocation())
                .from(dto.getFrom())
                .to(dto.getTo())
                .current(dto.getCurrent())
                .description(dto.getDescription())
                .build();
        experience.add(newExperience);
        p.setExperience(experience);
        profileRepository.save(p);
        return p;
    }

    @Override
    public Profile deleteEducation(String id) {
        Profile p = this.getMyProfile();
        var education = p.getEducation();
        EducationData e = education
                .stream()
                .filter(i -> id.equals(i.get_id())).reduce((a,b) -> {throw new IllegalStateException("At least two educations with the same ID found");})
                .orElseThrow(() -> new NoSuchElementException("No education with this ID found"));

        education.remove(e);
        p.setEducation(education);
        profileRepository.save(p);
        return p;
    }

    @Override
    public Profile deleteExperience(String id) {
        Profile p = this.getMyProfile();
        var experience = p.getExperience();
        ExperienceData e = experience
                .stream()
                .filter(i -> id.equals(i.get_id())).reduce((a,b) -> {throw new IllegalStateException("At least two experiences with the same ID found");})
                .orElseThrow(() -> new NoSuchElementException("No education with this ID found"));

        experience.remove(e);
        p.setExperience(experience);
        profileRepository.save(p);
        return p;
    }

    public Profile getUsersProfile(String id) {
        return mongoOperations.findOne(
                Query.query(Criteria.where("_id").is(id)),
                Profile.class,
                "profiles"
        );
    }
}
