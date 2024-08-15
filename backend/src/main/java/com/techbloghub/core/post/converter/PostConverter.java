package com.techbloghub.core.post.converter;

import com.techbloghub.common.domain.pagination.CursorPaged;
import com.techbloghub.common.domain.pagination.dto.CursorPagedResponse;
import com.techbloghub.core.post.domain.Post;
import com.techbloghub.core.post.presentation.dto.PostResponse;
import java.util.List;

public class PostConverter {
    
    public static CursorPagedResponse<PostResponse> convertResponse(CursorPaged<Post> cursorPaged) {
        List<PostResponse> postResponses = cursorPaged.getItems().stream()
            .map(PostResponse::of)
            .toList();
        return new CursorPagedResponse<>(postResponses, cursorPaged.getItemsPerPage(),
            cursorPaged.isHasMoreItems());
    }
}
