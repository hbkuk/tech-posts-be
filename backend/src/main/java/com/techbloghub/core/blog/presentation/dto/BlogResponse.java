package com.techbloghub.core.blog.presentation.dto;

import com.techbloghub.core.blog.domain.Blog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlogResponse {

    @Schema(description = "회사명 (한글)", example = "토스")
    private final String companyNameKorean;

    @Schema(description = "회사명 (영어)", example = "TOSS")
    private final String companyNameEnglish;

    @Schema(description = "블로그 URL", example = "https://toss.tech")
    private final String blogUrl;

    public static BlogResponse of(Blog blog) {
        return new BlogResponse(
            blog.getKoreanName(),
            blog.getEnglishName(),
            blog.getBlogUrl()
        );
    }
}