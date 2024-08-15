package com.techbloghub.core.post.domain;

import com.techbloghub.common.domain.pagination.CursorPaged;

public interface PostRepositoryCustom {
    CursorPaged<Post> findAllPostsByCondition(PostSearchCondition condition);
}