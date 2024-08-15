package com.techbloghub.common.domain.pagination.dto;

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