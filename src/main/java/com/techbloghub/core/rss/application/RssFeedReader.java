package com.techbloghub.core.rss.application;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import com.techbloghub.common.exception.common.ErrorCode;
import com.techbloghub.core.rss.exception.RssFeedReadException;
import com.techbloghub.core.rss.domain.RssFeed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
@Slf4j
public class RssFeedReader {

    private final RssReader rssReader;

    public List<RssFeed> read(String blogRssUrl) {
        return fetchRssFeedItems(blogRssUrl).map(RssFeed::new).collect(Collectors.toList());
    }

    private Stream<Item> fetchRssFeedItems(String blogRssUrl) {
        try {
            return rssReader.read(blogRssUrl);
        } catch (IOException e) {
            log.error("{} - Error reading RSS feed", blogRssUrl, e);
            throw new RssFeedReadException(ErrorCode.RSS_FEED_READ_FAILED);
        }
    }
}
