package com.techbloghub.core.post.presentation.dto;

import com.techbloghub.core.post.domain.PostSearchCondition;
import com.techbloghub.core.post.domain.Sort;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostSearchConditionRequest {
    
    @NotBlank
    private String sort;
    
    private String cursor;
    
    @Min(1)
    @Max(32)
    private int itemsPerPage;
    
    @Builder
    public PostSearchConditionRequest(String sort, int itemsPerPage) {
        this.sort = sort;
        this.itemsPerPage = itemsPerPage;
    }
    
    public PostSearchCondition toEntity() {
        return PostSearchCondition.builder()
            .sort(Sort.of(sort))
            .cursor(this.cursor)
            .itemsPerPage(this.itemsPerPage)
            .build();
    }
}
