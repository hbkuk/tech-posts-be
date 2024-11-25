package com.techbloghub.core.authentication.presentation;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

import com.techbloghub.core.authentication.application.OAuthService;
import com.techbloghub.core.authentication.domain.Tokens;
import com.techbloghub.core.authentication.presentation.dto.TokenResponse;
import com.techbloghub.core.authentication.presentation.dto.AuthorizationCodeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "인증")
@RestController
@RequiredArgsConstructor
public class OAuthController {
    
    public static final int COOKIE_AGE_SECONDS = 604800;
    
    private final OAuthService OAuthService;
    
    @Operation(summary = "소셜 로그인")
    @PostMapping("/api/social-login/{provider}")
    public ResponseEntity<TokenResponse> socialLogin(
        @Parameter(description = "Social 로그인 제공자명", example = "google") @PathVariable String provider,
        @Parameter(description = "인가 코드") @Valid @RequestBody AuthorizationCodeRequest request,
        HttpServletResponse response) {
        Tokens tokens = OAuthService.authenticate(provider, request);
        
        final ResponseCookie cookie = ResponseCookie.from("refresh-token",
                tokens.getRefreshToken())
            .maxAge(COOKIE_AGE_SECONDS)
            .sameSite("None")
            .secure(true)
            .httpOnly(true)
            .path("/")
            .build();
        response.addHeader(SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(TokenResponse.of(tokens.getAccessToken()));
    }
    
    @Operation(summary = "로그아웃")
    @DeleteMapping("/api/social-logout")
    public ResponseEntity<Void> socialLogout(@Parameter(hidden = true) @CookieValue("refresh-token") String refreshToken) {
        OAuthService.invalidate(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
