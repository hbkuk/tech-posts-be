package com.techbloghub.common.domain.pagination;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
public class PagedResponse<T> {

    private List<T> items;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;

    public static <T> PagedResponse<T> of(Page<T> page) {
        return new PagedResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getTotalPages(),
            page.getTotalElements(),
            page.getSize()
        );
    }
}