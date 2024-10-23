package com.techbloghub.common.scheduler;

import com.techbloghub.common.cache.CacheType;
import com.techbloghub.common.config.CacheConfig;
import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.rss.application.RssService;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile({"dev", "prod"})
public class RssFeedScheduler {
    
    private final RssService rssService;
    private final Queue<Blog> blogQueue = new ArrayDeque<>();
    private final CacheManager cacheManager;
    private final CacheConfig cacheConfig;
    
    private boolean isQueueReset = false;
    
    /**
     * 매일 새벽 3시에 Queue 초기화 후 Feed 수집 시작
     */
    @Scheduled(cron = "${rss-feed.scheduler.reset}", zone = "Asia/Seoul")
    public void resetQueueAndStartProcessing() {
        log.info("RSS 피드 작업을 위해 대기열을 초기화합니다.");
        
        resetQueue();
        isQueueReset = true;
        
        cacheConfig.clearCache(cacheManager, CacheType.POST); // TODO 캐시 삭제 관련 다른 방법 찾아보기
    }
    
    /**
     * 5분 간격으로 피드 수집 시작
     */
    @Scheduled(cron = "${rss-feed.scheduler.read}", zone = "Asia/Seoul")
    public void readRssFeeds() {
        if (!isQueueReset) {
            log.info("Queue가 초기화되지 않았으므로 작업을 실행하지 않습니다.");
            return;  // 큐가 초기화되지 않은 경우 작업 중지
        }
        
        if (blogQueue.isEmpty()) {
            log.info("모든 블로그의 RSS 피드를 읽었으므로, Queue가 초기화될 때까지 작업을 중지합니다.");
            isQueueReset = false;  // Queue가 비면 더 이상 작업을 수행하지 않음
            return;
        }
        
        Blog blog = blogQueue.poll();  // Queue에서 하나씩 꺼냄
        if (blog != null) {
            try {
                rssService.readRssFeeds(blog);
                log.info("RSS 피드를 성공적으로 읽었습니다. Blog 명: {}", blog.getEnglishName());
            } catch (Exception e) {
                log.error("RSS 피드를 읽는데 실패했습니다. Blog 명: {}", blog.getEnglishName(), e);
            }
        }
    }
    
    /**
     * Queue 초기화
     */
    private void resetQueue() {
        blogQueue.clear();
        blogQueue.addAll(Arrays.asList(Blog.values()));
        
        log.info("큐가 초기화되었습니다. {}개의 블로그가 대기열에 추가되었습니다.", blogQueue.size());
    }
}
