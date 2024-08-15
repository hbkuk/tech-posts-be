package com.techbloghub.common.domain.pagination;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CursorPaged<T> {
    
    private List<T> items;
    
    private int itemsPerPage;
    
    private boolean hasMoreItems;

}
