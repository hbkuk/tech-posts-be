package com.techbloghub.core.authentication.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationCodeRequest {
    
    @Schema(description = "인가 코드", example = "authorization_code")
    @NotBlank
    private String code;
}
