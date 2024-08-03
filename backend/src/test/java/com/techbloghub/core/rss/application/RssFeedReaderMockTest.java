package com.techbloghub.core.rss.application;

import com.apptasticsoftware.rssreader.DateTime;
import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import com.techbloghub.core.rss.domain.RssFeed;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static com.techbloghub.core.blog.domain.Blog.컬리;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Rss Feed Reader Mock 테스트")
@ExtendWith(MockitoExtension.class)
public class RssFeedReaderMockTest {

    RssFeedReader rssFeedReader;

    @Mock
    RssReader rssReader;

    @BeforeEach
    void 사전_객체_생성() {
        rssFeedReader = new RssFeedReader(rssReader);
    }

    @Nested
    class 피드_읽기 {

        @Test
        void 피드_읽기_성공() throws IOException {
            // given
            Item 컬리_첫번째_피드_아이템 = new Item(new DateTime());
            컬리_첫번째_피드_아이템.setTitle("함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 2부");
            컬리_첫번째_피드_아이템.setDescription("보완재 추천 모델을 서빙하기 위한 아키텍처 소개");
            컬리_첫번째_피드_아이템.setPubDate("Mon, 27 May 2024 10:00:00 +0900");
            컬리_첫번째_피드_아이템.setLink("http://thefarmersfront.github.io/blog/cart-recommend-model-development_second");

            Item 컬리_두번째_피드_아이템 = new Item(new DateTime());
            컬리_두번째_피드_아이템.setTitle("함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 1부");
            컬리_두번째_피드_아이템.setDescription("보완재 추천 모델을 적용하고 성과를 거둔 사례 소개");
            컬리_두번째_피드_아이템.setPubDate("Mon, 20 May 2024 13:00:00 +0900");
            컬리_두번째_피드_아이템.setLink("http://thefarmersfront.github.io/blog/cart-recommend-model-development");

            when(rssReader.read(any(InputStream.class))).thenReturn(Stream.of(컬리_첫번째_피드_아이템, 컬리_두번째_피드_아이템));


            // when
            List<RssFeed> 읽은_피드 = rssFeedReader.read(컬리.getBlogUrl());

            // then
            assertEquals(List.of(new RssFeed(컬리_첫번째_피드_아이템), new RssFeed(컬리_두번째_피드_아이템)), 읽은_피드);
        }
    }
}
