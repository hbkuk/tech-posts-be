package com.techbloghub.core.authentication.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    void deleteByToken(String token);
}
