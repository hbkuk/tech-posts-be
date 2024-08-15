package com.techbloghub.core.post.domain;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sort {
    LATEST("LATEST"),
    OLDEST("OLDEST");
    
    private final String value;
    
    public static Sort of(String value) {
        if(value == null || value.isBlank()) {
            return null;
        }
        return Arrays.stream(Sort.values())
            .filter(sort -> sort.value.equals(value))
            .findFirst()
            .orElse(null);
    }
}
