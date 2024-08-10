package com.techbloghub.core.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    @Query("select m from Member m where m.socialId = :socialId and m.oAuthProviderType = :oAuthProviderType")
    Optional<Member> findBySocialIdAndOAuthProviderType(
        @Param("socialId") String socialId,
        @Param("oAuthProviderType") OAuthProviderType oAuthProviderType);
    
}