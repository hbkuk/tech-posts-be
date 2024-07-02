package com.techbloghub.core.post.presentation.dto;

import com.techbloghub.core.post.domain.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {

    private final Long id;
    private final String link;
    private final String title;
    private final LocalDateTime publishAt;
    private final String description;

    public static PostResponse of(Post post) {
        return new PostResponse(
            post.getId(),
            post.getLink(),
            post.getTitle(),
            post.getPublishAt(),
            post.getDescription());
    }
}
