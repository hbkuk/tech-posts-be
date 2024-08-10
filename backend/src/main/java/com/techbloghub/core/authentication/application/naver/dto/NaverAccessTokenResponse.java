package com.techbloghub.core.authentication.application.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NaverAccessTokenResponse {
    
    @JsonProperty("token_type")
    public String tokenType;
    
    @JsonProperty("access_token")
    public String accessToken;
}
