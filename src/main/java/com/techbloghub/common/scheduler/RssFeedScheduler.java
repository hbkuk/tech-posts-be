package com.techbloghub.common.scheduler;

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
@Profile({"local", "dev", "prod"})
public class RssFeedScheduler {

    private final RssService rssService;

    @Scheduled(cron = "${rss.cron}")
    public void syncAllFeeds() {
        Arrays.stream(Blog.values()).forEach(blog -> {
            try {
                rssService.syncFeeds(blog);
            } catch (Exception e) {
                log.error("Failed to sync feeds for blog: {}", blog.getEnglishName(), e);
            }
        });
    }

}
