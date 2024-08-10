package com.techbloghub.core.authentication.application.kakao.dto;

import com.techbloghub.core.authentication.application.OauthUserProfile;
import com.techbloghub.core.member.domain.OAuthProviderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoProfileResponse implements OauthUserProfile {
    
    private Long id;
    
    private OAuthProviderType oAuthProviderType;
    
    public KakaoProfileResponse(Long id) {
        this.id = id;
    }
    
    public KakaoProfileResponse(Long id, OAuthProviderType oAuthProviderType) {
        this.id = id;
        this.oAuthProviderType = oAuthProviderType;
    }
    
    public static KakaoProfileResponse mergeOauthProviderName(
        KakaoProfileResponse response, OAuthProviderType oAuthProviderType) {
        return new KakaoProfileResponse(response.getId(), oAuthProviderType);
    }
    
    @Override
    public String getSocialId() {
        return id.toString();
    }
}
