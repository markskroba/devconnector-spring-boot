package com.markskroba.devconnectorspringboot.posts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeData {
    private String user;
}
