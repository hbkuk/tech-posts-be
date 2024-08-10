package com.techbloghub.core.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    
    @Column(name = "social_id")
    private String socialId;
    
    @Enumerated(EnumType.STRING)
    private OAuthProviderType oAuthProviderType;
    
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;
    
    @Builder
    private Member(String socialId, OAuthProviderType oAuthProviderType, MemberStatus memberStatus) {
        this.socialId = socialId;
        this.oAuthProviderType = oAuthProviderType;
        this.memberStatus = memberStatus;
    }
    
    public static Member of(final String socialId, final OAuthProviderType oAuthProviderType) {
        return Member.builder()
            .socialId(socialId)
            .oAuthProviderType(oAuthProviderType)
            .memberStatus(MemberStatus.ACTIVE)
            .build();
    }
}
