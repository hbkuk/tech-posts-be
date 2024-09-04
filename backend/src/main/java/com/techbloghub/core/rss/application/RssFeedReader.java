package com.techbloghub.core.rss.application;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import com.techbloghub.common.exception.common.ErrorCode;
import com.techbloghub.core.rss.domain.RssFeed;
import com.techbloghub.core.rss.exception.RssFeedReadException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class RssFeedReader {

    private final RssReader rssReader;

    public List<RssFeed> read(String blogRssUrl) {
        return fetchRssFeedItems(blogRssUrl)
            .map(RssFeed::new)
            .collect(Collectors.toList());
    }

    private Stream<Item> fetchRssFeedItems(String blogRssUrl) {
        try {
            String sanitizedXml = fetchAndSanitizeRssFeed(blogRssUrl);
            return rssReader.read(
                new ByteArrayInputStream(sanitizedXml.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            log.error("RSS Feed를 읽는데 실패했습니다. URL: {}, Error: {}, StackTrace: {}",
                blogRssUrl, e.getMessage(), getStackTrace(e));
            throw new RssFeedReadException(ErrorCode.RSS_FEED_READ_FAILED);
        }
    }
    
    private String fetchAndSanitizeRssFeed(String blogRssUrl) throws IOException {
        HttpURLConnection connection = createHttpConnection(blogRssUrl);
        String rawRssFeed = readInputStream(connection);
        return sanitizeRssFeed(rawRssFeed);
    }
    
    private HttpURLConnection createHttpConnection(String blogRssUrl) throws IOException {
        URL url = new URL(blogRssUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }
    
    private String readInputStream(HttpURLConnection connection) throws IOException {
        try (InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
    
    private String sanitizeRssFeed(String rawRssFeed) {
        // 유니코드 범위에서 유효한 문자들만 남기고 그 외의 문자 제거
        return rawRssFeed.replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", "");
    }

}
