package com.techbloghub.core.authentication.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Tokens {
    
    private final String accessToken;
    private final String refreshToken;
}
