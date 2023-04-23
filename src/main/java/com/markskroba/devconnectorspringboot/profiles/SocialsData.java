package com.markskroba.devconnectorspringboot.profiles;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SocialsData {
    private String youtube;
    private String facebook;
    private String twitter;
    private String linkedin;
    private String instagram;
}
