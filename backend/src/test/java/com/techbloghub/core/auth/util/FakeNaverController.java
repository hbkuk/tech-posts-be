package com.techbloghub.core.auth.util;


import com.techbloghub.common.exception.BadRequestException;
import com.techbloghub.common.exception.common.ErrorCode;
import com.techbloghub.core.auth.fixture.NaverMemberFixture;
import com.techbloghub.core.authentication.application.jwt.BearerAuthorizationExtractor;
import com.techbloghub.core.authentication.application.naver.NaverAuthProperties;
import com.techbloghub.core.authentication.application.naver.dto.NaverAccessTokenResponse;
import com.techbloghub.core.authentication.application.naver.dto.NaverProfileResponse;
import com.techbloghub.core.authentication.application.naver.dto.NaverProfileResponse.NaverUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RestController
public class FakeNaverController {
    
    @Autowired
    BearerAuthorizationExtractor bearerAuthorizationExtractor;
    
    @Autowired
    NaverAuthProperties naverAuthProperties;
    
    @PostMapping(value = "/naver/oauth/token")
    public ResponseEntity<NaverAccessTokenResponse> createToken(
        @RequestParam("grant_type") String grantType,
        @RequestParam("client_id") String clientId,
        @RequestParam("client_secret") String clientSecret,
        @RequestParam("code") String code,
        @RequestParam("state") String state) {
        validateRequestParam(grantType, clientId, clientSecret, state);
        NaverAccessTokenResponse response = new NaverAccessTokenResponse(
            "bearer",
            NaverMemberFixture.findTokenByCode(code)
        );
        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping(value = "/naver/v1/nid/me")
    public ResponseEntity<NaverProfileResponse> findProfile(
        @RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken) {
        String id = NaverMemberFixture.findIdByToken(
            bearerAuthorizationExtractor.extractAccessToken(accessToken));
        
        NaverProfileResponse profileResponse = new NaverProfileResponse(
            "00",
            "success",
            new NaverUserDetail(id));
        return ResponseEntity.ok(profileResponse);
    }
    
    private void validateRequestParam(String grantType, String clientId, String clientSecret,
        String state) {
        if (!naverAuthProperties.getClientId().equals(clientId)) {
            throw new BadRequestException(ErrorCode.UNPROCESSABLE_ENTITY);
        }
        if (!naverAuthProperties.getClientSecret().equals(clientSecret)) {
            throw new BadRequestException(ErrorCode.UNPROCESSABLE_ENTITY);
        }
        if (!"authorization_code".equals(grantType)) {
            throw new BadRequestException(ErrorCode.UNPROCESSABLE_ENTITY);
        }
        if (state.isBlank()) {
            throw new BadRequestException(ErrorCode.UNPROCESSABLE_ENTITY);
        }
    }
}
