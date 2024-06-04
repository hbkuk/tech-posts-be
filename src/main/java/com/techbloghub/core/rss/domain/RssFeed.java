package com.techbloghub.core.rss.domain;

import com.apptasticsoftware.rssreader.Item;
import com.techbloghub.common.util.DateConverter;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.techbloghub.common.util.StringUtil.getFilteredContent;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RssFeed {

    public static final int MAX_DESCRIPTION_LENGTH = 67;

    private String link;

    private String title;

    private LocalDateTime publishAt;

    private String description;

    public RssFeed(Item feed) {
        this.link = getLink(feed.getLink());
        this.title = getTitle(feed.getTitle());
        this.publishAt = getPublishDateOrNow(feed.getPubDate());
        this.description = getDescriptionOrDefault(feed.getDescription());
    }

    private String getDescriptionOrDefault(Optional<String> description) {
        return description
                .map(this::getDescription)
                .orElse("");
    }

    private String getDescription(String desc) {
        if (desc.length() < MAX_DESCRIPTION_LENGTH) {
            return getFilteredContent(desc);
        }
        return getFilteredContent(desc.substring(0, MAX_DESCRIPTION_LENGTH)) + "...";
    }

    private LocalDateTime getPublishDateOrNow(Optional<String> publishDate) {
        return publishDate.map(this::getLocalDateTime)
                .orElseGet(() -> LocalDateTime.now().withSecond(0).withNano(0));
    }

    private LocalDateTime getLocalDateTime(String pubDate) {
        try {
            return LocalDateTime.parse(DateConverter.convertRfc822ToIso8601(pubDate));
        } catch (ParseException e) {
            log.error("Error parsing", e);
            return LocalDateTime.now().withSecond(0).withNano(0);
        }
    }

    private String getTitle(Optional<String> title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("피드를 생성하기 위해서는 타이틀은 반드시 포함되어야 합니다.");
        }

        return getFilteredContent(title.get());
    }

    private String getLink(Optional<String> link) {
        if (link.isEmpty()) {
            throw new IllegalArgumentException("피드를 생성하기 위해서는 링크는 반드시 포함되어야 합니다.");
        }
        return link.get();
    }
}
