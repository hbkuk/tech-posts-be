package com.techbloghub.core.post.domain;

import com.techbloghub.common.domain.pagination.CursorPaged;
import com.techbloghub.core.blog.domain.Blog;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PostQueryRepository {
    CursorPaged<Post> findAllPostsByCondition(PostSearchCondition condition);
    
    Optional<LocalDateTime> findLatestPublishDate(Blog blog);
}