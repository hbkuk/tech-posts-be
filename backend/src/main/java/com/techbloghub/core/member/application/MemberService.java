package com.techbloghub.core.member.application;

import com.techbloghub.core.member.domain.Member;
import com.techbloghub.core.member.domain.MemberRepository;
import com.techbloghub.core.member.domain.OAuthProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepository memberRepository;
    
    @Transactional
    public Member findOrCreateMember(String socialId, OAuthProviderType type) {
        return memberRepository.findBySocialIdAndOAuthProviderType(socialId, type)
            .orElseGet(() -> memberRepository.save(
                Member.of(socialId, type))
            );
    }
}
