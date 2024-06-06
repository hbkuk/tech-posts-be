package com.techbloghub.core.post.presentation.dto;

import com.techbloghub.core.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PostResponse {

    private final Long id;
    private final String link;
    private final String title;
    private final LocalDateTime publishAt;
    private final String description;

    public static List<PostResponse> of(List<Post> posts) {
        return posts.stream()
                .map(post -> {
                    return new PostResponse(
                            post.getId(),
                            post.getLink(),
                            post.getTitle(),
                            post.getPublishAt(),
                            post.getDescription()
                    );
                }).collect(Collectors.toList());
    }
}
