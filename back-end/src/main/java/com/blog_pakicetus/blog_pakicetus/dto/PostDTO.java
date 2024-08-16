package com.blog_pakicetus.blog_pakicetus.dto;

import lombok.Data;

@Data
public class PostDTO {
    private Long userId;
    private String title;
    private String content;
}
