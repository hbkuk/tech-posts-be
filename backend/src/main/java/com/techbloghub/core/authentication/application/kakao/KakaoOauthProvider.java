package com.techbloghub.core.authentication.application.kakao;

import com.techbloghub.common.util.WebClientUtil;
import com.techbloghub.core.authentication.application.OAuthProvider;
import com.techbloghub.core.authentication.application.OauthUserProfile;
import com.techbloghub.core.authentication.application.kakao.dto.KakaoAccessTokenRequest;
import com.techbloghub.core.authentication.application.kakao.dto.KakaoAccessTokenResponse;
import com.techbloghub.core.authentication.application.kakao.dto.KakaoProfileResponse;
import com.techbloghub.core.member.domain.OAuthProviderType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@AllArgsConstructor
@Slf4j
public class KakaoOauthProvider implements OAuthProvider {
    
    private static final OAuthProviderType PROVIDER_TYPE = OAuthProviderType.KAKAO;
    
    private final WebClientUtil webClientUtil;
    private final KakaoAuthProperties kakaoAuthProperties;
    private final KakaoUserProperties kakaoUserProperties;
    
    @Override
    public OAuthProviderType getOAuthProviderType() {
        return PROVIDER_TYPE;
    }
    
    @Override
    public boolean is(String providerType) {
        return PROVIDER_TYPE.equals(OAuthProviderType.of(providerType));
    }
    
    @Override
    public OauthUserProfile getUserProfile(String code) {
        String accessToken = requestKakaoToken(code);
        return requestKakaoUserInfo(accessToken);
    }
    
    private String requestKakaoToken(String code) {
        return webClientUtil
            .post(kakaoAuthProperties.getTokenUri(),
                toFormData(createKakaoAccessTokenRequest(code)),
                MediaType.APPLICATION_FORM_URLENCODED,
                KakaoAccessTokenResponse.class)
            .doOnError(ex -> log.error("Error requesting Kakao token", ex))
            .block()
            .getAccessToken();
    }
    
    private KakaoProfileResponse requestKakaoUserInfo(String accessToken) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        
        KakaoProfileResponse response = webClientUtil
            .get(kakaoUserProperties.getProfileUri(),
                headers,
                KakaoProfileResponse.class)
            .doOnError(ex -> log.error("Error requesting Kakao User Info: {}", ex.getMessage()))
            .block();
        return KakaoProfileResponse.mergeOauthProviderName(response, PROVIDER_TYPE);
    }
    
    private KakaoAccessTokenRequest createKakaoAccessTokenRequest(String code) {
        return KakaoAccessTokenRequest.of(code,
            kakaoAuthProperties.getClientId(),
            kakaoAuthProperties.getRedirectUri()
        );
    }
    
    private MultiValueMap<String, String> toFormData(KakaoAccessTokenRequest request) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", request.getGrantType());
        formData.add("code", request.getCode());
        formData.add("client_id", request.getClientId());
        formData.add("redirect_uri", request.getRedirectUri());
        return formData;
    }
}
