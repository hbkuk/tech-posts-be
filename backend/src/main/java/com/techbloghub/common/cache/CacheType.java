package com.techbloghub.common.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {
    POST("post", 12, 10000);

    private final String cacheName;
    private final int expiredAfterWrite;
    private final int maximumSize;
}