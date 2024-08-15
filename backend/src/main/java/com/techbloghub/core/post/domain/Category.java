package com.techbloghub.core.post.domain;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    ALL("ALL");
    
    private final String value;
    
    public static Category of(String value) {
        return Arrays.stream(Category.values())
            .filter(category -> category.value.equals(value))
            .findFirst()
            .orElse(null);
    }
    
}
