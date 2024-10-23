package com.techbloghub.core.authentication.application.naver;

import com.techbloghub.common.util.WebClientUtil;
import com.techbloghub.core.authentication.application.OAuthProvider;
import com.techbloghub.core.authentication.application.OauthUserProfile;
import com.techbloghub.core.authentication.application.naver.dto.NaverAccessTokenRequest;
import com.techbloghub.core.authentication.application.naver.dto.NaverAccessTokenResponse;
import com.techbloghub.core.authentication.application.naver.dto.NaverProfileResponse;
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
public class NaverOauthProvider implements OAuthProvider {
    
    private static final OAuthProviderType PROVIDER_TYPE = OAuthProviderType.NAVER;
    
    private final WebClientUtil webClientUtil;
    private final NaverAuthProperties naverAuthProperties;
    private final NaverUserProperties naverUserProperties;
    
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
        String accessToken = requestNaverToken(code);
        return requestNaverUserInfo(accessToken);
    }
    
    private String requestNaverToken(String code) {
        return webClientUtil
            .post(naverAuthProperties.getTokenUri(),
                toFormData(createNaverAccessTokenRequest(code)),
                MediaType.APPLICATION_FORM_URLENCODED,
                NaverAccessTokenResponse.class)
            .doOnError(ex -> log.error("Error requesting Naver token", ex))
            .block()
            .getAccessToken();
    }
    
    private NaverProfileResponse requestNaverUserInfo(String accessToken) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        
        NaverProfileResponse response = webClientUtil
            .get(naverUserProperties.getProfileUri(),
                headers,
                NaverProfileResponse.class)
            .doOnError(ex -> log.error("Error requesting Naver User Info: {}", ex.getMessage()))
            .block();
        return NaverProfileResponse.mergeOauthProviderName(response, PROVIDER_TYPE);
    }
    
    private NaverAccessTokenRequest createNaverAccessTokenRequest(String code) {
        return new NaverAccessTokenRequest(
            "authorization_code",
            code,
            naverAuthProperties.getClientId(),
            naverAuthProperties.getClientSecret(),
            naverAuthProperties.getRedirectUri(),
            "STATE_STRING"
        );
    }
    
    private MultiValueMap<String, String> toFormData(NaverAccessTokenRequest request) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", request.getGrantType());
        formData.add("code", request.getCode());
        formData.add("client_id", request.getClientId());
        formData.add("client_secret", request.getClientSecret());
        formData.add("redirect_uri", request.getRedirectUri());
        formData.add("state", request.getState());
        return formData;
    }
}
