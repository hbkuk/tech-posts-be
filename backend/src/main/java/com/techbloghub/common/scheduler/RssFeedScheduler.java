package com.techbloghub.common.scheduler;

import com.techbloghub.common.alert.rss.SlackInfoLogger;
import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.rss.application.RssService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile({"dev", "prod"})
public class RssFeedScheduler {

    private final RssService rssService;
    
    @SlackInfoLogger
    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
    public void readAllRssFeeds() {
        Arrays.stream(Blog.values()).forEach(blog -> {
            try {
                rssService.readRssFeeds(blog);
            } catch (Exception e) {
                log.error("RSS 피드를 읽는데 실패했습니다. Blog 명: {}", blog.getEnglishName(), e);
            }
        });
    }
}