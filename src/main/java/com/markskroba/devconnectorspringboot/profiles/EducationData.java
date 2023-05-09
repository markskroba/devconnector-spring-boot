package com.markskroba.devconnectorspringboot.profiles;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
public class EducationData {
    @Id
    private String _id;
    private String school;
    private String degree;
    private String fieldofstudy;
    private Date from;
    private Date to;
    private Boolean current;
    private String description;
}
