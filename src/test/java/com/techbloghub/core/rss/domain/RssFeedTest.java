package com.techbloghub.core.rss.domain;

import com.apptasticsoftware.rssreader.DateTime;
import com.apptasticsoftware.rssreader.Item;
import com.techbloghub.common.util.converter.DateConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Rss Feed 테스트")
public class RssFeedTest {

    @Nested
    class 피드_생성 {

        @Nested
        class 성공 {

            @Test
            @DisplayName("필수 값이 모두 존재하는 경우 정상적으로 피드가 생성된다.")
            void 필수_값이_모두_존재하는_경우_피드_생성() {
                // given
                Item 피드_아이템 = new Item(new DateTime());
                피드_아이템.setTitle("함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 2부");
                피드_아이템.setDescription("보완재 추천 모델을 서빙하기 위한 아키텍처 소개");
                피드_아이템.setPubDate("Mon, 27 May 2024 10:00:00 +0900");
                피드_아이템.setLink("http://thefarmersfront.github.io/blog/cart-recommend-model-development_second");

                // when
                RssFeed 피드 = new RssFeed(피드_아이템);

                // then
                assertAll(
                        () -> assertEquals(피드_아이템.getTitle().get(), 피드.getTitle()),
                        () -> assertEquals(피드_아이템.getDescription().get(), 피드.getDescription()),
                        () -> assertEquals(LocalDateTime.parse(DateConverter.convertRfc822ToIso8601(피드_아이템.getPubDate().get())), 피드.getPublishAt()),
                        () -> assertEquals(피드_아이템.getLink().get(), 피드.getLink())
                );
            }

            @Test
            void 날짜가_누락된_경우_임시_날짜로_대체된다() {
                // given
                Item 피드_아이템 = new Item(new DateTime());
                피드_아이템.setTitle("함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 2부");
                피드_아이템.setDescription("보완재 추천 모델을 서빙하기 위한 아키텍처 소개");
                피드_아이템.setLink("http://thefarmersfront.github.io/blog/cart-recommend-model-development_second");

                // when
                RssFeed 피드 = new RssFeed(피드_아이템);

                // then
                assertThat(피드.getPublishAt()).isEqualToIgnoringSeconds(LocalDateTime.now());
            }

            @Test
            void 설명이_누락된_경우_빈_문자열로_대체된다() {
                // given
                Item 피드_아이템 = new Item(new DateTime());
                피드_아이템.setTitle("함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 2부");
                피드_아이템.setPubDate("Mon, 27 May 2024 10:00:00 +0900");
                피드_아이템.setLink("http://thefarmersfront.github.io/blog/cart-recommend-model-development_second");

                // when
                RssFeed 피드 = new RssFeed(피드_아이템);

                // then
                assertThat(피드.getDescription()).isEqualTo("");
            }

        }

        @Nested
        class 실패 {

            @Nested
            class 타이틀_누락 {
                @Test
                @DisplayName("타이틀이 누락된 경우 피드가 생성될 수 없다.")
                void 타이틀이_누락된_경우_피드_생성_안됨() {
                    // given
                    Item 피드_아이템 = new Item(new DateTime());
                    피드_아이템.setDescription("보완재 추천 모델을 서빙하기 위한 아키텍처 소개");
                    피드_아이템.setPubDate("Mon, 27 May 2024 10:00:00 +0900");
                    피드_아이템.setLink("http://thefarmersfront.github.io/blog/cart-recommend-model-development_second");

                    // when, then
                    assertThatExceptionOfType(IllegalArgumentException.class)
                            .isThrownBy(() -> {
                                new RssFeed(피드_아이템);
                            })
                            .withMessageMatching("피드를 생성하기 위해서는 타이틀은 반드시 포함되어야 합니다.");
                }
            }

            @Nested
            class 링크_누락 {
                @Test
                @DisplayName("타이틀이 누락된 경우 피드가 생성될 수 없다.")
                void 타이틀이_누락된_경우_피드_생성_안됨() {
                    // given
                    Item 피드_아이템 = new Item(new DateTime());
                    피드_아이템.setTitle("함께 구매하면 좋은 상품이에요! - 장바구니 추천 개발기 2부");
                    피드_아이템.setDescription("보완재 추천 모델을 서빙하기 위한 아키텍처 소개");
                    피드_아이템.setPubDate("Mon, 27 May 2024 10:00:00 +0900");

                    // when, then
                    assertThatExceptionOfType(IllegalArgumentException.class)
                            .isThrownBy(() -> {
                                new RssFeed(피드_아이템);
                            })
                            .withMessageMatching("피드를 생성하기 위해서는 링크는 반드시 포함되어야 합니다.");
                }
            }
        }
    }
}
