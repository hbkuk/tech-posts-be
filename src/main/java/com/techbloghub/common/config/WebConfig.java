package com.techbloghub.common.config;

import com.techbloghub.common.domain.pagination.PaginationArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PaginationArgumentResolver paginationArgumentResolver;

    public WebConfig(PaginationArgumentResolver paginationArgumentResolver) {
        this.paginationArgumentResolver = paginationArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(paginationArgumentResolver);
    }
}
