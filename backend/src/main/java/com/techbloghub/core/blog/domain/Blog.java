package com.techbloghub.core.blog.domain;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Blog {
    
    카카오("카카오", "KAKAO", "https://tech.kakao.com/", "https://tech.kakao.com/feed/", "RSS"),
    라인("라인", "LINE", "https://techblog.lycorp.co.jp/ko/", "https://techblog.lycorp.co.jp/ko/feed/index.xml", "RSS"),
    뱅크샐러드("뱅크샐러드", "BANK SALAD", "https://blog.banksalad.com", "https://blog.banksalad.com/rss.xml", "RSS"),
    토스("토스", "TOSS", "https://toss.tech", "https://toss.tech/rss.xml", "RSS"),
    데브시스터즈("데브시스터즈", "DEVSISTERS", "https://tech.devsisters.com", "https://tech.devsisters.com/rss.xml", "RSS"),
    컬리("컬리", "KURLY", "https://helloworld.kurly.com", "https://helloworld.kurly.com/feed.xml", "RSS"),
    인프랩("인프랩", "INFLAB", "https://tech.inflab.com", "https://tech.inflab.com/rss.xml", "RSS"),
    우아한형제들("우아한형제들", "WOOWAHAN", "https://techblog.woowahan.com", "https://techblog.woowahan.com/feed/", "RSS"),
    요기요("요기요", "YOGIYO", "https://medium.com/deliverytechkorea", "https://medium.com/feed/deliverytechkorea", "RSS"),
    줌("줌", "ZUM", "https://zuminternet.github.io", "https://zuminternet.github.io/feed.xml", "RSS"),
    엔에치엔("엔에치엔", "NHN", "https://meetup.nhncloud.com", "https://meetup.nhncloud.com/rss", "RSS"),
    쿠팡("쿠팡", "COUPANG", "https://medium.com/coupang-engineering", "https://medium.com/feed/coupang-engineering", "RSS"),
    무신사("무신사", "MUSINSA", "https://medium.com/29cm", "https://medium.com/feed/29cm", "RSS"),
    당근마켓("당근마켓", "DAANGN", "https://medium.com/daangn", "https://medium.com/feed/daangn", "RSS"),
    직방("직방", "ZIGBANG", "https://medium.com/zigbang", "https://medium.com/feed/zigbang", "RSS"),
    왓챠("왓챠", "WATCHA", "https://medium.com/watcha", "https://medium.com/feed/watcha", "RSS"),
    하이퍼커넥트("하이퍼커넥트", "HYPERCONNECT", "https://hyperconnect.github.io", "https://hyperconnect.github.io/feed.xml", "ATOM"),
    쏘카("쏘카", "SOCAR", "https://tech.socarcorp.kr", "https://tech.socarcorp.kr/feed", "ATOM"),
    리디("리디", "RIDI", "https://www.ridicorp.com", "https://www.ridicorp.com/feed", "RSS"),
    사람인("사람인", "SARAMIN", "https://saramin.github.io/", "https://saramin.github.io/feed.xml", "RSS");
    
    private final String koreanName;
    private final String englishName;
    private final String blogUrl;
    private final String rssFeedUrl;
    private final String feedType;
    
    public static Blog of(String value) {
        if(value == null || value.isBlank()) {
            return null;
        }
        return Arrays.stream(Blog.values())
            .filter(blog -> blog.englishName.equals(value.toUpperCase()))
            .findFirst()
            .orElse(null);
    }
}
