package com.techbloghub.common.alert.rss;

import static com.techbloghub.common.alert.rss.RssMessageGenerator.TOTAL_TIMING_TEMPLATE;

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
public class RssFeedTimingAspect {

    private final AlertSender alertSender;

    @Value("${slack.webhook.rss-reader-url}")
    private String hookUri;

    @Around("execution(* com.techbloghub.core.rss.application.RssService.readRssFeeds(..)) && args(blog)")
    public Object measureFeedSyncTime(ProceedingJoinPoint joinPoint, Blog blog) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            String errorMessage = RssMessageGenerator.generateErrorMessage(e);
            log.error(errorMessage);
            alertSender.send(errorMessage, hookUri);
            throw e;
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            String successMessage = RssMessageGenerator.generateSuccessMessage(blog.getEnglishName(), duration);
            log.debug(successMessage);
            alertSender.send(successMessage, hookUri);
        }
    }
}
