package com.techbloghub.core.authentication.application.jwt;

import static com.techbloghub.common.exception.common.ErrorCode.INVALID_ACCESS_TOKEN;

import com.techbloghub.common.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

@Component
public class BearerAuthorizationExtractor {
    
    private static final String BEARER_TYPE = "Bearer ";
    
    public String extractAccessToken(String header) {
        if (header != null && header.startsWith(BEARER_TYPE)) {
            return header.substring(BEARER_TYPE.length()).trim();
        }
        throw new UnauthorizedException(INVALID_ACCESS_TOKEN);
    }
}
