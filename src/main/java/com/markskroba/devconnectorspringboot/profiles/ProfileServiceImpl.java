package com.markskroba.devconnectorspringboot.profiles;

import com.markskroba.devconnectorspringboot.auth.jwt.JwtAuthService;
import com.markskroba.devconnectorspringboot.users.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final JwtAuthService authService;
    private final MongoOperations mongoOperations;

    @Override
    public Profile getMyProfile() {
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        ObjectId id = new ObjectId(user.get_id());
        Profile p = mongoOperations.findOne(Query.query(Criteria.where("user").is(id)), Profile.class);
        if (p == null) throw new NoSuchElementException("No profile for this user");
        return p;
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }


}
