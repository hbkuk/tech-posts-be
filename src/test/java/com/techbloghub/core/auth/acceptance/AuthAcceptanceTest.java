package com.techbloghub.core.auth.acceptance;

import com.techbloghub.common.util.AcceptanceTest;
import com.techbloghub.core.authentication.presentation.dto.KakaoCodeRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.techbloghub.core.auth.fixture.KakaoMemberFixture.라이언;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("인증 인수 테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Nested
    class 카카오_로그인 {

        @Nested
        class 성공 {

            // TODO: 가입되지 않은 회원 -> 회원 저장 -> 토큰 발급

            /**
             * When 클라이언트는 카카오로부터 전달받은 정보로 로그인을 요청한다.
             * Then 회원 가입 처리 후 토큰이 발급된다.
             */
            @Test
            void 가입되지_않은_회원에게_토큰_발급() {
                // given
                KakaoCodeRequest request = new KakaoCodeRequest(라이언.인가_코드);

                // when
                ExtractableResponse<Response> 카카오_로그인_요청_응답 = given()
                        .body(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .when()
                        .post("/api/auth/kakao")
                        .then().log().all()
                        .statusCode(HttpStatus.OK.value())
                        .extract();

                // then
                String 발급된_액세스_토큰 = 카카오_로그인_요청_응답.jsonPath().getString("accessToken");
                assertThat(발급된_액세스_토큰).isNotBlank();

                String 발급된_리프레시_토큰 = 카카오_로그인_요청_응답.cookie("refresh-token");
                assertThat(발급된_리프레시_토큰).isNotBlank();
            }

            // TODO: 이미 가입된 회원 -> 토큰 발급

            /**
             * Given 클라이언트는 카카오를 통해 회원가입을 진행한다.
             * When  클라이언트는 카카오로부터 전달받은 정보로 로그인을 요청한다.
             * Then  회원 가입은 생략되고, 토큰이 발급된다.
             */
            @Test
            void 이미_가입된_회원에게_토큰_발급() {
                // given

                // when

                // then
            }

        }

        @Nested
        class 실패 {

            // TODO: 코드, redirect url 등등의 정보를 전달하지 않은 경우

            /**
             * When  클라이언트는 카카오로부터 전달받은 정보를 일부 포함하지 않고 로그인을 요청한다.
             * Then  회원 가입이 진행되지 않는다.
             */
            @Test
            void 이미_가입된_회원에게_토큰_발급() {
                // given

                // when

                // then
            }
        }
    }

    @Nested
    class 로그아웃 {

        @Nested
        class 성공 {
            // TODO: Access Token 인 메모리 DB 추가, 블랙리스트 테이블에 Refresh Token 추가

            @Test
            void 로그아웃_처리() {
                // given

                // when

                // then
            }
        }

        @Nested
        class 실패 {

            // TODO: 두개의 토큰을 주지않은 경우
        }
    }

}
