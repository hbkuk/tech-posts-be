package com.techbloghub.core.authentication.application;

import com.techbloghub.core.member.domain.OAuthProviderType;

public interface OAuthProvider {
    
    OAuthProviderType getOAuthProviderType();
    
    boolean is(String providerType);
    
    OauthUserProfile getUserProfile(String code);
    
}
