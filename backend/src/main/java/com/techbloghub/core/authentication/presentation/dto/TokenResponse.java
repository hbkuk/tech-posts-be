package com.techbloghub.core.authentication.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenResponse {
    
    private String accessToken;
    
    public static TokenResponse of(String accessToken) {
        return new TokenResponse(accessToken);
    }
}
