package com.markskroba.devconnectorspringboot.posts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCommentDto {
    private String text;
}
