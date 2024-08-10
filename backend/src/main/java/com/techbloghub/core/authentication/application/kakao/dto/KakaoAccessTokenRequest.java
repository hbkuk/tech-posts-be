package com.techbloghub.core.authentication.application.kakao.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class KakaoAccessTokenRequest {
    
    private static final String DEFAULT_GRANT_TYPE = "authorization_code";
    
    private String grantType;
    
    private String code;
    
    private String clientId;
    
    private String redirectUri;
    
    public static KakaoAccessTokenRequest of(final String code, final String clientId, final String redirectUri) {
        return new KakaoAccessTokenRequest(DEFAULT_GRANT_TYPE, code, clientId, redirectUri);
    }
}
