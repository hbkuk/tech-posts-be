package com.techbloghub.common.domain.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

@Getter
@AllArgsConstructor
@Schema(description = "페이지네이션 요청")
public class PaginationRequest {

    @Schema(description = "페이지 번호", example = "3")
    private int number;

    @Schema(description = "페이지 크기", example = "10")
    private int size;

    @Schema(description = "정렬 기준", example = "createdAt")
    private String sortBy;

    @Schema(description = "정렬 방향", allowableValues = {"ASC", "DESC"}, example = "DESC")
    private String direction;

    public PageRequest toEntity() {
        return PageRequest.of(number, size, Direction.fromString(direction), sortBy);
    }
}
