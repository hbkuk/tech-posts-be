package com.techbloghub.core.rss.domain;

import com.apptasticsoftware.rssreader.DateTime;
import com.apptasticsoftware.rssreader.Item;
import com.techbloghub.common.util.converter.DateConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Nested
    class 발행_일자_가져오기 {

        @DisplayName("피드 아이템의 발행 일자가 ISO-8601 타입일 경우 RFC-882 타입으로 변환된다.")
        @ParameterizedTest
        @CsvSource(value = {
                "Thu, 18 Jul 2019 18:00:00 +0900$2019-07-18T18:00:00",
                "Mon, 01 Jan 2020 00:00:00 +0900$2020-01-01T00:00:00",
                "Wed, 25 Dec 2019 12:30:00 +0900$2019-12-25T12:30:00",
                "Sun, 15 Mar 2020 23:45:00 +0900$2020-03-15T23:45:00",
                "Fri, 10 Apr 2020 09:15:00 +0900$2020-04-10T09:15:00"
        }, delimiter = '$')
        void 발행_일자를_RFC_882_타입으로_변환(String RFC_822_타입의_날짜_문자열, String ISO_8601_타입의_날짜_문자열) {
            // when
            LocalDateTime ISO_8601_타입으로_변환된_날짜 = RssFeed.getPublishDateOrNow(Optional.of(RFC_822_타입의_날짜_문자열));

            // then
            assertEquals(LocalDateTime.parse(ISO_8601_타입의_날짜_문자열), ISO_8601_타입으로_변환된_날짜);
        }

        @DisplayName("피드 아이템의 발행 일자가 날짜 데이터가 아닌 문자열일 경우 현재 날짜로 변환된다.")
        @ValueSource(strings = {"Invalid Date", "Error", "null"})
        @ParameterizedTest
        void 날짜_데이터가_아닌_경우_현재_날짜로_변환(String 날짜_데이터가_아닌_문자열) {
            // when
            LocalDateTime 변환된_데이터 = RssFeed.getPublishDateOrNow(Optional.of(날짜_데이터가_아닌_문자열));

            // then
            assertEquals(LocalDateTime.now().withSecond(0).withNano(0), 변환된_데이터);
        }
    }

    @Nested
    class 피드_설명_가져오기 {

        @Test
        void 피드_설명_그대로_가져오기() {
            // given
            String 피드_설명 = "보완재 추천 모델을 서빙하기 위한 아키텍처 소개";

            // when
            String 가져온_피드_설명 = RssFeed.getDescriptionOrDefault(Optional.of(피드_설명));

            // then
            assertEquals(피드_설명, 가져온_피드_설명);
        }

        @DisplayName("피드의 설명이 길 경우, 일부 설명은 생략된다.")
        @Test
        void 긴_피드_설명일_경우_일부_문자열은_생략() {
            // given
            String 기존_피드_설명 = "누가 읽으면 좋을까? 카프카(Kafka)가 무엇인지 알고 있는 독자를 대상으로 합니다. 기술적 구현방식을 다루기보단 카프카를 기반으로 한 다양한 기술적 개념에 대해서 얇고 넓게 소개합니다.";

            // when
            String 생략된_설명 = RssFeed.getDescriptionOrDefault(Optional.of(기존_피드_설명));

            // then
            String 예상되는_설명 = "누가 읽으면 좋을까? 카프카(Kafka)가 무엇인지 알고 있는 독자를 대상으로 합니다. 기술적 구현방식을 다루기보단 카프...";
            assertEquals(예상되는_설명, 생략된_설명);
        }

        @Test
        void 설명이_없을_경우_빈_문자열() {
            // when
            String 비어있는_설명 = RssFeed.getDescriptionOrDefault(Optional.empty());

            // then
            assertEquals("", 비어있는_설명);
        }
    }
}
