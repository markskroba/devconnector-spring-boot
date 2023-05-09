package com.markskroba.devconnectorspringboot.profiles;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.markskroba.devconnectorspringboot.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
public class ProfileWithUserData {
    private String _id;
    @Field(targetType = FieldType.OBJECT_ID)
    private User user;
    private String company;
    private String website;
    private String location;
    private String status;
    private List<String> skills;
    private String bio;
    private String githubuser;
    private List<ExperienceData> experience;
    private List<EducationData> education;
    private SocialsData socials;
    private Date date;
}
