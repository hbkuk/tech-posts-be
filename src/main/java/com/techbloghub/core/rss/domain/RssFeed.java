package com.techbloghub.core.rss.domain;

import com.apptasticsoftware.rssreader.Item;
import com.techbloghub.common.util.converter.DateConverter;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
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

    private static void validateFeed(Item feedItem) {
        if (feedItem.getLink().isEmpty()) {
            throw new IllegalArgumentException("피드를 생성하기 위해서는 링크는 반드시 포함되어야 합니다.");
        }
        if (feedItem.getTitle().isEmpty()) {
            throw new IllegalArgumentException("피드를 생성하기 위해서는 타이틀은 반드시 포함되어야 합니다.");
        }
    }

    public static String getDescriptionOrDefault(Optional<String> description) {
        if (description.isEmpty()) {
            return "";
        }
        if (description.get().length() < 67) {
            return description.get();
        }
        return description.get().substring(0, 67) + "...";
    }

    public static LocalDateTime getPublishDateOrNow(Optional<String> publishDate) {
        return publishDate.map(pubDate -> {
                    try {
                        return LocalDateTime.parse(DateConverter.convertRfc822ToIso8601(pubDate));
                    } catch (ParseException e) {
                        log.error("Error parsing", e);
                        return LocalDateTime.now();
                    }
                })
                .orElseGet(() -> LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
