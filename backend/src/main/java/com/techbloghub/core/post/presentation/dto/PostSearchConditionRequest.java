package com.techbloghub.core.post.presentation.dto;

import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.post.domain.PostSearchCondition;
import com.techbloghub.core.post.domain.Sort;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostSearchConditionRequest {
    
    @NotBlank
    private String sort;
    
    private String cursor;
    
    @Min(1)
    @Max(32)
    private int itemsPerPage;
    
    @Nullable
    private String blog;
    
    @Builder
    public PostSearchConditionRequest(String sort, int itemsPerPage, String blog) {
        this.sort = sort;
        this.itemsPerPage = itemsPerPage;
        this.blog = blog;
    }
    
    public PostSearchCondition toEntity() {
        return PostSearchCondition.builder()
            .sort(Sort.of(sort))
            .cursor(this.cursor)
            .itemsPerPage(this.itemsPerPage)
            .blog(this.blog != null ? Blog.of(this.blog) : null)
            .build();
    }
}
