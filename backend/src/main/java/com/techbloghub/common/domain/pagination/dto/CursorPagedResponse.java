package com.techbloghub.common.domain.pagination.dto;

import com.techbloghub.common.domain.pagination.CursorPaged;
import com.techbloghub.core.post.domain.Post;
import com.techbloghub.core.post.presentation.dto.PostResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CursorPagedResponse<T> {

    private List<T> items;
    
    private int itemsPerPage;
    
    private boolean hasMoreItems;
    
}