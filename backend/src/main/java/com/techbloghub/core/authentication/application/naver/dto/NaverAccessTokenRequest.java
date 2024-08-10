package com.techbloghub.core.authentication.application.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NaverAccessTokenRequest {
    
    private String grantType;
    
    private String code;
    
    private String clientId;
    
    private String clientSecret;
    
    private String redirectUri;
    
    private String state;
    
}
