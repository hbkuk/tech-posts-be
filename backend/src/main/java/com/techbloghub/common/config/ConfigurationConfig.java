package com.techbloghub.common.config;

import com.techbloghub.core.authentication.application.kakao.KakaoAuthProperties;
import com.techbloghub.core.authentication.application.kakao.KakaoUserProperties;
import com.techbloghub.core.authentication.application.naver.NaverAuthProperties;
import com.techbloghub.core.authentication.application.naver.NaverUserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value =
    {
        KakaoAuthProperties.class, KakaoUserProperties.class,
        NaverAuthProperties.class, NaverUserProperties.class
        
    })
public class ConfigurationConfig {

}
