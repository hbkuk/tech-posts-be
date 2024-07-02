package com.techbloghub.common.domain.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "페이지네이션 응답")
public class PagedResponse<T> {

    @Schema(description = "목록")
    private List<T> items;

    @Schema(description = "현재 페이지", example = "1")
    private int currentPage;

    @Schema(description = "총 페이지 수", example = "10")
    private int totalPages;

    @Schema(description = "총 아이템 수", example = "100")
    private long totalItems;

    @Schema(description = "페이지 크기", example = "10")
    private int pageSize;
}