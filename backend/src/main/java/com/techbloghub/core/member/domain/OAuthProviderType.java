package com.techbloghub.core.member.domain;

import static com.techbloghub.common.exception.common.ErrorCode.NOT_SUPPORTED_OAUTH_SERVICE;

import com.techbloghub.common.exception.UnsupportedOAuthTypeException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OAuthProviderType {
    NORMAL,
    KAKAO,
    NAVER;
    
    public static OAuthProviderType of(String providerType) {
        return Arrays.stream(values())
            .filter(value -> value.name().equalsIgnoreCase(providerType))
            .findFirst()
            .orElseThrow(() -> new UnsupportedOAuthTypeException(NOT_SUPPORTED_OAUTH_SERVICE));
    }
}
