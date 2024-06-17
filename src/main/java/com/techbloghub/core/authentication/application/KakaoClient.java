package com.techbloghub.core.authentication.application;

import com.techbloghub.common.util.WebClientUtil;
import com.techbloghub.core.authentication.application.dto.KakaoAccessTokenRequest;
import com.techbloghub.core.authentication.application.dto.KakaoAccessTokenResponse;
import com.techbloghub.core.authentication.application.dto.KakaoProfileResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@AllArgsConstructor
public class KakaoClient {

    private final WebClientUtil webClientUtil;
    private final KakaoAuthProperties kakaoAuthProperties;
    private final KakaoUserProperties kakaoUserProperties;

    public KakaoProfileResponse requestKakaoProfile(String code) {
        String accessToken = requestKakaoToken(code);
        return requestKakaoUserInfo(accessToken);
    }

    private String requestKakaoToken(String code) {
        return webClientUtil
                .post(kakaoAuthProperties.getTokenUri(),
                        createKakaoAccessTokenRequest(code),
                        MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE),
                        KakaoAccessTokenResponse.class)
                .block()
                .getAccessToken();
    }

    private KakaoAccessTokenRequest createKakaoAccessTokenRequest(String code) {
        return new KakaoAccessTokenRequest(
                code,
                kakaoAuthProperties.getClientId(),
                kakaoAuthProperties.getRedirectUri()
        );
    }

    private KakaoProfileResponse requestKakaoUserInfo(String accessToken) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8");

        return webClientUtil
                .get(kakaoUserProperties.getProfileUri(),
                        headers,
                        KakaoProfileResponse.class)
                .block();
    }
}
