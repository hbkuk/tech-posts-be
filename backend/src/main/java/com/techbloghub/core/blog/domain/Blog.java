package com.techbloghub.core.blog.domain;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Blog {
    
    카카오("카카오", "KAKAO", "https://tech.kakao.com/", "https://tech.kakao.com/feed/"),
    라인("라인", "LINE", "https://techblog.lycorp.co.jp/ko/", "https://techblog.lycorp.co.jp/ko/feed/index.xml"),
    뱅크샐러드("뱅크샐러드", "BANK SALAD", "https://blog.banksalad.com", "https://blog.banksalad.com/rss.xml"),
    토스("토스", "TOSS", "https://toss.tech", "https://toss.tech/rss.xml"),
    데브시스터즈("데브시스터즈", "DEVSISTERS", "https://tech.devsisters.com", "https://tech.devsisters.com/rss.xml"),
    컬리("컬리", "KURLY", "https://helloworld.kurly.com", "https://helloworld.kurly.com/feed.xml"),
    인프랩("인프랩", "INFLAB", "https://tech.inflab.com", "https://tech.inflab.com/rss.xml"),
    우아한형제들("우아한형제들", "WOOWAHAN", "https://techblog.woowahan.com", "https://techblog.woowahan.com/feed/"),
    요기요("요기요", "YOGIYO", "https://medium.com/deliverytechkorea", "https://medium.com/feed/deliverytechkorea"),
    줌("줌", "ZUM", "https://zuminternet.github.io", "https://zuminternet.github.io/feed.xml"),
    엔에치엔("엔에치엔", "NHN", "https://meetup.nhncloud.com", "https://meetup.nhncloud.com/rss"),
    쿠팡("쿠팡", "COUPANG", "https://medium.com/coupang-engineering", "https://medium.com/feed/coupang-engineering"),
    무신사("무신사", "MUSINSA", "https://medium.com/musinsa-tech", "https://medium.com/feed/musinsa-tech"),
    당근마켓("당근마켓", "DAANGN", "https://medium.com/daangn", "https://medium.com/feed/daangn"),
    직방("직방", "ZIGBANG", "https://medium.com/zigbang", "https://medium.com/feed/zigbang"),
    왓챠("왓챠", "WATCHA", "https://medium.com/watcha", "https://medium.com/feed/watcha"),
    하이퍼커넥트("하이퍼커넥트", "HYPERCONNECT", "https://hyperconnect.github.io", "https://hyperconnect.github.io/feed.xml"),
    쏘카("쏘카", "SOCAR", "https://tech.socarcorp.kr", "https://tech.socarcorp.kr/feed"),
    리디("리디", "RIDI", "https://www.ridicorp.com", "https://www.ridicorp.com/feed");
    
    private final String koreanName;
    private final String englishName;
    private final String blogUrl;
    private final String rssFeedUrl;
    
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
