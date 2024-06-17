package com.techbloghub.core.authentication.application;

import com.techbloghub.core.authentication.application.dto.KakaoProfileResponse;
import com.techbloghub.core.authentication.domain.MemberTokens;
import com.techbloghub.core.authentication.domain.RefreshToken;
import com.techbloghub.core.authentication.domain.RefreshTokenRepository;
import com.techbloghub.core.authentication.presentation.dto.KakaoCodeRequest;
import com.techbloghub.core.authentication.presentation.dto.TokenResponse;
import com.techbloghub.core.member.application.MemberService;
import com.techbloghub.core.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenService {

    private final MemberService memberService;
    private final KakaoClient kakaoClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse generateToken(KakaoCodeRequest request) {
        KakaoProfileResponse kakaoProfileResponse = kakaoClient.requestKakaoProfile(request.getCode());
        Member member = memberService.findOrCreateMemberByKakaoId(kakaoProfileResponse.getId());

        MemberTokens memberTokens = jwtTokenProvider.generateLoginToken(member.getId().toString());
        RefreshToken savedRefreshToken = new RefreshToken(memberTokens.getRefreshToken(),
                member.getId());
        refreshTokenRepository.save(savedRefreshToken);
        return TokenResponse.of(memberTokens.getAccessToken(), memberTokens.getRefreshToken());
    }
}
