package com.techbloghub.core.authentication.application;

import com.techbloghub.core.authentication.application.jwt.JwtTokenProvider;
import com.techbloghub.core.authentication.domain.MemberTokens;
import com.techbloghub.core.authentication.domain.RefreshToken;
import com.techbloghub.core.authentication.domain.RefreshTokenRepository;
import com.techbloghub.core.authentication.presentation.dto.OAuthProviderCodeRequest;
import com.techbloghub.core.member.application.MemberService;
import com.techbloghub.core.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TokenService {
    
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final OauthProviders oauthProviders;
    
    @Transactional
    public MemberTokens generateToken(String providerName, OAuthProviderCodeRequest request) {
        OAuthProvider oAuthProvider = oauthProviders.map(providerName);
        OauthUserProfile userProfile = oAuthProvider.getUserProfile(
            request.getCode());
        Member member = memberService.findOrCreateMember(userProfile.getSocialId(),
            oAuthProvider.getOAuthProviderType());
        
        MemberTokens memberTokens = jwtTokenProvider.generateLoginToken(member.getId().toString());
        RefreshToken savedRefreshToken = new RefreshToken(memberTokens.getRefreshToken(),
            member.getId());
        refreshTokenRepository.save(savedRefreshToken);
        return memberTokens;
    }
    
    @Transactional
    public void removeRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
