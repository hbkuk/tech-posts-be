package com.techbloghub.core.authentication.application.naver;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("naver.user")
@ToString
public class NaverUserProperties {
    
    private String profileUri;
}
