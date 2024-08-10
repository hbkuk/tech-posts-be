package com.techbloghub.core.authentication.application.naver;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("naver.auth")
@ToString
public class NaverAuthProperties {
    
    private String tokenUri;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
