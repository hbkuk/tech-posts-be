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

    @Transactional
    public void syncFeeds(Blog blog) {
        List<RssFeed> rssFeeds = rssFeedReader.read(blog.getBlogUrl());

        Optional<LocalDateTime> latestPublishDate = postService.getLatestPublishDate(blog);

        if (latestPublishDate.isEmpty()) {
            List<PostCreateRequest> createRequests = rssFeeds.stream()
                    .map(rssFeed -> PostCreateRequest.of(
                            blog,
                            rssFeed.getLink(),
                            rssFeed.getTitle(),
                            rssFeed.getPublishAt(),
                            rssFeed.getDescription()
                    )).collect(Collectors.toList());

            postService.registerPost(createRequests);
        } else {
            List<PostCreateRequest> createRequests = rssFeeds.stream()
                    .filter(rssFeed -> rssFeed.getPublishAt().isAfter(latestPublishDate.get()))
                    .map(rssFeed -> PostCreateRequest.of(
                            blog,
                            rssFeed.getLink(),
                            rssFeed.getTitle(),
                            rssFeed.getPublishAt(),
                            rssFeed.getDescription()
                    ))
                    .collect(Collectors.toList());

            if (!createRequests.isEmpty()) {
                postService.registerPost(createRequests);
            }
        }
    }
}
