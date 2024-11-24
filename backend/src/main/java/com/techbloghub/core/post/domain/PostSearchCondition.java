package com.techbloghub.core.post.domain;

import com.techbloghub.core.blog.domain.Blog;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSearchCondition {
    
    private final Sort sort;
    private final String cursor;
    private final int itemsPerPage;
    private final Blog blog;
    
    @Builder
    public PostSearchCondition(Sort sort, String cursor, int itemsPerPage, Blog blog) {
        this.sort = sort;
        this.cursor = cursor;
        this.itemsPerPage = itemsPerPage;
        this.blog = blog;
    }
}
