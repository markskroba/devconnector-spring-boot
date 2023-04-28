package com.markskroba.devconnectorspringboot.profiles.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProfileDto {
    private String company;
    private String website;
    private String location;
    private String bio;
    private String status;
    private String githubusername;
    private String skills;
    private String youtube;
    private String facebook;
    private String twitter;
    private String instagram;
    private String linkedin;

}
