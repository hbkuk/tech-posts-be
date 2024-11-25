package com.techbloghub.core.authentication.application;

import com.techbloghub.core.authentication.application.jwt.JwtTokenProvider;
import com.techbloghub.core.authentication.domain.Tokens;
import com.techbloghub.core.authentication.domain.RefreshToken;
import com.techbloghub.core.authentication.domain.RefreshTokenRepository;
import com.techbloghub.core.authentication.presentation.dto.AuthorizationCodeRequest;
import com.techbloghub.core.member.application.MemberService;
import com.techbloghub.core.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OAuthService {
    
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final OauthProviders oauthProviders;
    
    @Transactional
    public Tokens authenticate(String providerName, AuthorizationCodeRequest request) {
        OAuthProvider oAuthProvider = oauthProviders.map(providerName);
        OauthUserProfile userProfile = oAuthProvider.getUserProfile(
            request.getCode());
        Member member = memberService.findOrCreateMember(userProfile.getSocialId(),
            oAuthProvider.getOAuthProviderType());
        
        Tokens tokens = jwtTokenProvider.generateLoginToken(member.getId().toString());
        RefreshToken savedRefreshToken = new RefreshToken(tokens.getRefreshToken(),
            member.getId());
        refreshTokenRepository.save(savedRefreshToken);
        return tokens;
    }
    
    @Transactional
    public void invalidate(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
