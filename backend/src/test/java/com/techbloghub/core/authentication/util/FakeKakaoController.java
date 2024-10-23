package com.techbloghub.core.authentication.util;


import com.techbloghub.core.authentication.fixture.KakaoMemberFixture;
import com.techbloghub.core.authentication.application.jwt.BearerAuthorizationExtractor;
import com.techbloghub.core.authentication.application.kakao.dto.KakaoAccessTokenResponse;
import com.techbloghub.core.authentication.application.kakao.dto.KakaoProfileResponse;
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
public class FakeKakaoController {
    
    @Autowired
    BearerAuthorizationExtractor bearerAuthorizationExtractor;
    
    @PostMapping(value = "/kakao/oauth/token", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<KakaoAccessTokenResponse> createToken(
        @RequestParam("code") String code,
        @RequestParam("client_id") String clientId,
        @RequestParam("redirect_uri") String redirectUri,
        @RequestParam("grant_type") String grantType) {
        KakaoAccessTokenResponse response = new KakaoAccessTokenResponse(
            "bearer",
            KakaoMemberFixture.findTokenByCode(code)
        );
        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping(value = "/kakao/v2/user/me")
    public ResponseEntity<KakaoProfileResponse> findProfile(
        @RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken,
        @RequestHeader(value = HttpHeaders.CONTENT_TYPE,
            defaultValue = "application/x-www-form-urlencoded;charset=utf-8") String contentType) {
        KakaoProfileResponse profileResponse = new KakaoProfileResponse(
            KakaoMemberFixture.findIdByToken(
                bearerAuthorizationExtractor.extractAccessToken(accessToken))
        );
        return ResponseEntity.ok(profileResponse);
    }
}
