package com.techbloghub.common.domain.pagination;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PaginationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PaginationDefault.class);
    }

    @Override
    public PaginationRequest resolveArgument(MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
        PaginationDefault paginationDefault = parameter.getParameterAnnotation(
            PaginationDefault.class);
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        int page = parseOrDefault(request.getParameter("page"), paginationDefault.page());
        int size = parseOrDefault(request.getParameter("size"), paginationDefault.size());
        String sortBy = getOrDefault(request.getParameter("sortBy"), paginationDefault.sort());
        String direction = getOrDefault(request.getParameter("direction"),
            paginationDefault.direction());

        return new PaginationRequest(page, size, sortBy, direction);
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
