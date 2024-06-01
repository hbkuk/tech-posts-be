package com.techbloghub.core.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Blog {

    카카오("KAKAO", "https://tech.kakao.com/feed/"),
    라인("LINE", "https://techblog.lycorp.co.jp/ko/feed/index.xml"),
    뱅크샐러드("BANK SALAD", "https://blog.banksalad.com/rss.xml"),
    토스("TOSS", "https://toss.tech/rss.xml"),
    데브시스터즈("DEVSISTERS", "https://tech.devsisters.com/rss.xml"),
    컬리("KURLY", "https://helloworld.kurly.com/feed.xml"),
    인프랩("INFLAB", "https://tech.inflab.com/rss.xml"),
    우아한형제들("WOOWAHAN", "https://techblog.woowahan.com/feed/");

    private final String name;
    private final String blogUrl;

}
