package com.techbloghub.core.auth.step;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.techbloghub.core.authentication.presentation.dto.OAuthProviderCodeRequest;
import com.techbloghub.core.member.domain.OAuthProviderType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class AuthSteps {
    
    public static ExtractableResponse<Response> 소셜_로그인_요청(OAuthProviderCodeRequest OAuth_제공자_코드_정보,
        OAuthProviderType OAuth_제공자_유형) {
        return given().log().all()
            .body(OAuth_제공자_코드_정보)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/login/{oauth_provider}", OAuth_제공자_유형.name().toLowerCase())
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract();
    }
    
    public static void 실패하는_소셜_로그인_요청(OAuthProviderType OAuth_제공자_유형) {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/login/{oauth_provider}", OAuth_제공자_유형.name().toLowerCase())
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract();
    }
    
    public static void 로그아웃_요청(String 리프레시_토큰) {
        given().log().all()
            .cookie("refresh-token", 리프레시_토큰)
            .when()
            .delete("/api/logout")
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value())
            .extract();
    }
    
    public static void 실패하는_로그아웃_요청() {
        given().log().all()
            .when()
            .delete("/api/logout")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract();
    }
    
    
    public static void 토큰_확인(ExtractableResponse<Response> 소셜_로그인_요청_응답) {
        String 발급된_액세스_토큰 = 소셜_로그인_요청_응답.jsonPath().getString("accessToken");
        assertThat(발급된_액세스_토큰).isNotBlank();
        
        String 발급된_리프레시_토큰 = 소셜_로그인_요청_응답.cookie("refresh-token");
        assertThat(발급된_리프레시_토큰).isNotBlank();
    }
}
