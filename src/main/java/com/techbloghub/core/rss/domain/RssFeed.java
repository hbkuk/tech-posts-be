package com.techbloghub.core.rss.domain;

import com.apptasticsoftware.rssreader.Item;
import com.techbloghub.common.util.converter.DateConverter;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RssFeed {

    private String link;

    private String title;

    private LocalDateTime publishAt;

    private String description;

    public RssFeed(Item feed) {
        validateFeed(feed);

        this.link = feed.getLink().get();
        this.title = feed.getTitle().get();
        this.publishAt = getPublishDateOrNow(feed.getPubDate());
        this.description = getDescriptionOrDefault(feed.getDescription());
    }

    public static RssFeed of(String link, String title, LocalDateTime publishAt, String description) {
        return new RssFeed(link, title, publishAt, description); // TODO: 정적 팩토리 메서드는 삭제해야겠다..
    }

    private static void validateFeed(Item feedItem) {
        if (feedItem.getLink().isEmpty()) {
            throw new IllegalArgumentException("피드를 생성하기 위해서는 링크는 반드시 포함되어야 합니다.");
        }
        if (feedItem.getTitle().isEmpty()) {
            throw new IllegalArgumentException("피드를 생성하기 위해서는 타이틀은 반드시 포함되어야 합니다.");
        }
    }

    private static String getDescriptionOrDefault(Optional<String> feed) {
        return feed.orElse("");
    }

    private static LocalDateTime getPublishDateOrNow(Optional<String> feed) {
        return feed.map(pubDate -> LocalDateTime.parse(DateConverter.convertRfc822ToIso8601(pubDate)))
                .orElseGet(() -> LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
