package com.techbloghub.core.rss.application;

import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.post.application.PostService;
import com.techbloghub.core.post.application.dto.PostCreateRequest;
import com.techbloghub.core.rss.domain.RssFeed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RssService {

    private final RssFeedReader rssFeedReader;

    private final PostService postService;

    private static PostCreateRequest convertPostCreateRequest(Blog blog, RssFeed rssFeed) {
        return PostCreateRequest.of(
                blog,
                rssFeed.getLink(),
                rssFeed.getTitle(),
                rssFeed.getPublishAt(),
                rssFeed.getDescription()
        );
    }

    @Transactional
    public void syncFeeds(Blog blog) {
        List<RssFeed> rssFeeds = rssFeedReader.read(blog.getBlogUrl());
        Optional<LocalDateTime> latestPublishDate = postService.getLatestPublishDate(blog);

        List<PostCreateRequest> createRequests = getPostCreateRequests(blog, rssFeeds, latestPublishDate);
        if (!createRequests.isEmpty()) {
            postService.registerPost(createRequests);
        }
    }

    private List<PostCreateRequest> getPostCreateRequests(Blog blog, List<RssFeed> rssFeeds, Optional<LocalDateTime> latestPublishDate) {
        return latestPublishDate
                .map(date -> filterAndConvertRssFeeds(blog, rssFeeds, date))
                .orElseGet(() -> convertAllRssFeeds(blog, rssFeeds));
    }

    private List<PostCreateRequest> filterAndConvertRssFeeds(Blog blog, List<RssFeed> rssFeeds, LocalDateTime latestPublishDate) {
        return rssFeeds.stream()
                .filter(rssFeed -> rssFeed.getPublishAt().isAfter(latestPublishDate))
                .map(rssFeed -> convertPostCreateRequest(blog, rssFeed))
                .collect(Collectors.toList());
    }

    private List<PostCreateRequest> convertAllRssFeeds(Blog blog, List<RssFeed> rssFeeds) {
        return rssFeeds.stream()
                .map(rssFeed -> convertPostCreateRequest(blog, rssFeed))
                .collect(Collectors.toList());
    }
}
