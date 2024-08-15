package com.techbloghub.common.domain.pagination;

import com.techbloghub.common.domain.pagination.dto.OffsetPaginationRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class OffsetPaginationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OffsetPaginationDefault.class);
    }

    @Override
    public OffsetPaginationRequest resolveArgument(MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) {
        OffsetPaginationDefault offsetPaginationDefault = parameter.getParameterAnnotation(
            OffsetPaginationDefault.class);
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        int number = parseOrDefault(request.getParameter("number"), offsetPaginationDefault.page());
        int size = parseOrDefault(request.getParameter("size"), offsetPaginationDefault.size());
        String sortBy = getOrDefault(request.getParameter("sortBy"), offsetPaginationDefault.sort());
        String direction = getOrDefault(request.getParameter("direction"),
            offsetPaginationDefault.direction());

        return new OffsetPaginationRequest(number, size, sortBy, direction);
    }

    private int parseOrDefault(String param, int defaultValue) {
        try {
            return (param != null) ? Integer.parseInt(param) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String getOrDefault(String param, String defaultValue) {
        return (param != null) ? param : defaultValue;
    }
}
