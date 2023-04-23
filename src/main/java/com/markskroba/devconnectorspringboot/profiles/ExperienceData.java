package com.markskroba.devconnectorspringboot.profiles;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExperienceData {
    private String title;
    private String company;
    private String location;
    private Date from;
    private Date to;
    private Boolean current;
    private String description;
}
