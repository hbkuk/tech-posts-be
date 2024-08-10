package com.techbloghub.core.authentication.application.kakao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("kakao.auth")
@ToString
public class KakaoAuthProperties {
    
    private String tokenUri;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
