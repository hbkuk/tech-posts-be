package com.techbloghub.core.post.application.dto;

import com.techbloghub.core.blog.domain.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostCreateRequest {

    private Blog blog;

    private String link;

    private String title;

    private LocalDateTime postedAt;

    private String description;

    public static PostCreateRequest of(Blog blog, String link, String title, LocalDateTime postedAt, String description) {
        return new PostCreateRequest(blog, link, title, postedAt, description);
    }
}
