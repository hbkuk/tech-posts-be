package com.techbloghub.core.authentication.application.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techbloghub.core.authentication.application.OauthUserProfile;
import com.techbloghub.core.member.domain.OAuthProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverProfileResponse implements OauthUserProfile {
    
    @JsonProperty("resultcode")
    private String resultCode;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("response")
    private NaverUserDetail naverUserDetail;
    
    private OAuthProviderType oAuthProviderType;
    
    public NaverProfileResponse(String id) {
        this.naverUserDetail = new NaverUserDetail(id);
    }
    
    public NaverProfileResponse(String id, OAuthProviderType oAuthProviderType) {
        this.naverUserDetail = new NaverUserDetail(id);
        this.oAuthProviderType = oAuthProviderType;
    }
    
    public NaverProfileResponse(String resultCode, String message,
        NaverUserDetail naverUserDetail) {
        this.resultCode = resultCode;
        this.message = message;
        this.naverUserDetail = naverUserDetail;
    }
    
    public static NaverProfileResponse mergeOauthProviderName(
        NaverProfileResponse response, OAuthProviderType oAuthProviderType) {
        return new NaverProfileResponse(response.getSocialId(), oAuthProviderType);
    }
    
    @Override
    public String getSocialId() {
        return naverUserDetail.getId();
    }
    
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverUserDetail {
        
        private String id;
    }
}
