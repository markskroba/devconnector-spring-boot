package com.markskroba.devconnectorspringboot.profiles;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
public class ExperienceData {
    @Id
    private ObjectId _id;
    private String title;
    private String company;
    private String location;
    private Date from;
    private Date to;
    private Boolean current;
    private String description;
}
