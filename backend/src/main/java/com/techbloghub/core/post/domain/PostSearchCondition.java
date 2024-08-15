package com.techbloghub.core.post.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSearchCondition {
    
    private final Sort sort;
    private final String cursor;
    private final int itemsPerPage;
    
    @Builder
    public PostSearchCondition(Sort sort, String cursor, int itemsPerPage) {
        this.sort = sort;
        this.cursor = cursor;
        this.itemsPerPage = itemsPerPage;
    }
}
