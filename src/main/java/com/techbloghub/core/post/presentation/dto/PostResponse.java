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

    @Schema(description = "번호", example = "1")
    private final Long id;

    @Schema(description = "링크", example = "https://toss.tech/article/firesidechat_frontend_2")
    private final String link;

    @Schema(description = "제목", example = "모닥불 | EP.2 함수형 프로그래밍, 프론트엔드 개발에 진짜 도움 될까?")
    private final String title;

    @Schema(description = "발행 일시", example = "2024-03-13 12:00:00", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime publishAt;

    @Schema(description = "내용", example = "토스 프론트엔드 개발자들은 함수형 프로그래밍 (Functional Programming) 과 객체 지향 프로그래밍 (Ob...")
    private final String description;

    @Schema(description = "블로그 정보")
    private final BlogResponse blogResponse;

    public static PostResponse of(Post post) {
        return new PostResponse(
            post.getId(),
            post.getLink(),
            post.getTitle(),
            post.getPublishAt(),
            post.getDescription(),
            BlogResponse.of(post.getBlog())
        );
    }
}
