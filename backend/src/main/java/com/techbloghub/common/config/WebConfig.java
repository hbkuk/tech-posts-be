package com.techbloghub.common.config;

import com.techbloghub.common.domain.pagination.OffsetPaginationArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final OffsetPaginationArgumentResolver offsetPaginationArgumentResolver;

    public WebConfig(OffsetPaginationArgumentResolver offsetPaginationArgumentResolver) {
        this.offsetPaginationArgumentResolver = offsetPaginationArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(offsetPaginationArgumentResolver);
    }
}
