package com.techbloghub.core.authentication.application.kakao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("kakao.user")
@ToString
public class KakaoUserProperties {
    
    private String profileUri;
}
