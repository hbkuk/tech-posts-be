package com.techbloghub.core.post.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techbloghub.core.blog.presentation.dto.BlogResponse;
import com.techbloghub.core.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {

    private final Long id;

    private final String link;

    private final String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime publishAt;

    private final BlogResponse blogResponse;

    public static PostResponse of(Post post) {
        return new PostResponse(
            post.getId(),
            post.getLink(),
            post.getTitle(),
            post.getPublishAt(),
            BlogResponse.of(post.getBlog())
        );
    }
}
