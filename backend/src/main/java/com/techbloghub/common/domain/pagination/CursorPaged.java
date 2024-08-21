package com.techbloghub.common.domain.pagination;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CursorPaged<T> {
    
    private final List<T> items;
    
    private final int itemsPerPage;
    
    private final boolean hasMoreItems;
    
    private final String lastCursor;
}
