package com.techbloghub.core.rss.domain;

import com.apptasticsoftware.rssreader.Item;
import com.techbloghub.common.util.converter.DateConverter;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    public RssFeed(Item feedItem) {
        if (feedItem.getLink().isEmpty()) {
            throw new IllegalArgumentException("피드를 생성하기 위해서는 링크는 반드시 포함되어야 합니다.");
        }
        if (feedItem.getTitle().isEmpty()) {
            throw new IllegalArgumentException("피드를 생성하기 위해서는 타이틀은 반드시 포함되어야 합니다.");
        }

        this.link = feedItem.getLink().get();
        this.title = feedItem.getTitle().get();
        this.publishAt = feedItem.getPubDate().isPresent()
                ? LocalDateTime.parse(DateConverter.convertRfc822ToIso8601(feedItem.getPubDate().get()))
                : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.description = feedItem.getDescription().isPresent() ? feedItem.getDescription().get() : "";
    }


    public static RssFeed of(String link, String title, LocalDateTime publishAt, String description) {
        return new RssFeed(link, title, publishAt, description); // TODO: 정적 팩토리 메서드는 삭제해야겠다..
    }
}
