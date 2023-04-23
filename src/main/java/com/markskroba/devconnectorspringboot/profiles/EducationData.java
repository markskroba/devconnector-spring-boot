package com.markskroba.devconnectorspringboot.profiles;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EducationData {
    private String school;
    private String degree;
    private String fieldofstudy;
    private Date from;
    private Date to;
    private Boolean current;
    private String description;
}
