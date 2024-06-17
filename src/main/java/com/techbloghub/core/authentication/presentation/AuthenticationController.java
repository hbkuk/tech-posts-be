package com.techbloghub.core.authentication.presentation;

import com.techbloghub.core.authentication.application.TokenService;
import com.techbloghub.core.authentication.presentation.dto.KakaoCodeRequest;
import com.techbloghub.core.authentication.presentation.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final TokenService tokenService;

    @PostMapping("/api/auth/kakao")
    public ResponseEntity<TokenResponse> generateTokenFromKakao(@RequestBody KakaoCodeRequest request) {
        return ResponseEntity.ok(tokenService.generateToken(request));
    }

}
