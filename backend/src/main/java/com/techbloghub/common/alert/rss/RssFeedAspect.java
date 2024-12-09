package com.techbloghub.common.alert.rss;

import com.techbloghub.common.alert.sender.AlertSender;
import com.techbloghub.core.blog.domain.Blog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RssFeedAspect {

    private final AlertSender alertSender;

    @Value("${slack.webhook.rss-reader-url}")
    private String hookUri;

    @Around("execution(* com.techbloghub.core.rss.application.RssService.readRssFeeds(..)) && args(blog)")
    public Object notifyErrorMessage(ProceedingJoinPoint joinPoint, Blog blog) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            String errorMessage = RssMessageGenerator.generateErrorMessage(e);
            alertSender.send(errorMessage, hookUri);
            throw e;
        }
    }
}
