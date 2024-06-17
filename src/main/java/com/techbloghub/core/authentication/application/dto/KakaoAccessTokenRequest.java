package com.techbloghub.core.authentication.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoAccessTokenRequest {

    private final String code;
    private final String clientId;
    private final String redirectUri;

}
