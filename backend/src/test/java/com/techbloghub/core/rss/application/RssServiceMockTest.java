package com.techbloghub.core.rss.application;


import com.techbloghub.core.post.application.PostService;
import com.techbloghub.core.rss.domain.RssFeed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.techbloghub.core.blog.domain.Blog.컬리;
import static org.mockito.Mockito.*;

@DisplayName("Rss 서비스 Mock 테스트")
@ExtendWith(MockitoExtension.class)
public class RssServiceMockTest {

    RssService rssService;

    @Mock
    RssFeedReader rssFeedReader;

    @Mock
    PostService postService;

    @BeforeEach
    void 사전_객체_생성() {
        rssService = new RssService(rssFeedReader, postService);
    }

    @Nested
    class 새로운_피드_가져오기 {

        @Test
        void 새로운_피드_동기화() {
            // given
            RssFeed 컬리_첫번째_피드 = new RssFeed(
                    "http://thefarmersfront.github.io/blog/cart-recommend-model-development_second",
                    "함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 2부",
                    LocalDateTime.parse("2019-07-18T18:00:00")
            );

            RssFeed 컬리_두번째_피드 = new RssFeed(
                    "http://thefarmersfront.github.io/blog/cart-recommend-model-development",
                    "함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 1부",
                    LocalDateTime.parse("2019-07-18T09:15:00")
            );

            when(rssFeedReader.read(컬리.getRssFeedUrl())).thenReturn(List.of(컬리_첫번째_피드, 컬리_두번째_피드));

            // when
            rssService.syncFeeds(컬리);

            // then
            verify(postService, times(1)).getLatestPublishDate(컬리);
            verify(postService, times(1)).registerPost(anyList());
        }

        @Test
        void 부분적으로_새로운_피드가_있음() {
            // given
            RssFeed 컬리_첫번째_피드 = new RssFeed(
                    "http://thefarmersfront.github.io/blog/cart-recommend-model-development_second",
                    "함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 2부",
                    LocalDateTime.parse("2019-07-18T18:00:00")
            );

            RssFeed 컬리_두번째_피드 = new RssFeed(
                    "http://thefarmersfront.github.io/blog/cart-recommend-model-development",
                    "함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 1부",
                    LocalDateTime.parse("2019-07-18T09:15:00")
            );

            when(rssFeedReader.read(컬리.getRssFeedUrl())).thenReturn(List.of(컬리_첫번째_피드, 컬리_두번째_피드));

            when(postService.getLatestPublishDate(컬리)).thenReturn(Optional.of(LocalDateTime.parse("2019-07-18T17:00:00")));

            // when
            rssService.syncFeeds(컬리);

            // then
            verify(postService, times(1)).getLatestPublishDate(컬리);
            verify(postService, times(1)).registerPost(anyList());
        }

        @Test
        void 새로운_피드가_없음() {
            // given
            RssFeed 컬리_첫번째_피드 = new RssFeed(
                    "http://thefarmersfront.github.io/blog/cart-recommend-model-development_second",
                    "함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 2부",
                    LocalDateTime.parse("2019-07-18T18:00:00")
            );

            RssFeed 컬리_두번째_피드 = new RssFeed(
                    "http://thefarmersfront.github.io/blog/cart-recommend-model-development",
                    "함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 1부",
                    LocalDateTime.parse("2019-07-18T09:15:00")
            );

            when(rssFeedReader.read(컬리.getRssFeedUrl())).thenReturn(List.of(컬리_첫번째_피드, 컬리_두번째_피드));

            when(postService.getLatestPublishDate(컬리)).thenReturn(Optional.of(LocalDateTime.parse("2019-07-18T18:00:00")));

            // when
            rssService.syncFeeds(컬리);

            // then
            verify(postService, times(1)).getLatestPublishDate(컬리);
            verify(postService, never()).registerPost(anyList());
        }
    }
}
