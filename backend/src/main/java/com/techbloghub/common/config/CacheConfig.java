package com.techbloghub.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.techbloghub.common.cache.CacheType;
import java.util.Objects;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableCaching
public class CacheConfig {
    
    public void clearCache(CacheManager cacheManager, CacheType cacheType) {
        Objects.requireNonNull(cacheManager.getCache(cacheType.getCacheName())).clear();
    }

    @Bean
    public List<CaffeineCache> caffeineCaches() {
        return Arrays.stream(CacheType.values())
                .map(cache -> new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder().recordStats()
                        .expireAfterWrite(cache.getExpiredAfterWrite(), TimeUnit.HOURS) // TODO: 캐시 만료 정책 수정
                        .maximumSize(cache.getMaximumSize())
                        .build()))
                .collect(Collectors.toList());
    }

    @Bean
    public CacheManager cacheManager(List<CaffeineCache> caffeineCaches) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caffeineCaches);

        return cacheManager;
    }
}