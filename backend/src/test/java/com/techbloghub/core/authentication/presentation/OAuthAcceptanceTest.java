package com.techbloghub.core.authentication.presentation;

import static com.techbloghub.core.authentication.fixture.KakaoMemberFixture.라이언;
import static com.techbloghub.core.authentication.fixture.NaverMemberFixture.나나;
import static com.techbloghub.core.authentication.step.OAuthSteps.로그아웃_요청;
import static com.techbloghub.core.authentication.step.OAuthSteps.소셜_로그인_요청;
import static com.techbloghub.core.authentication.step.OAuthSteps.실패하는_로그아웃_요청;
import static com.techbloghub.core.authentication.step.OAuthSteps.실패하는_소셜_로그인_요청;
import static com.techbloghub.core.authentication.step.OAuthSteps.토큰_확인;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.techbloghub.common.AcceptanceTest;
import com.techbloghub.core.authentication.application.OAuthService;
import com.techbloghub.core.authentication.domain.Tokens;
import com.techbloghub.core.authentication.presentation.dto.AuthorizationCodeRequest;
import com.techbloghub.core.member.domain.OAuthProviderType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("인증 인수 테스트")
@Disabled
public class OAuthAcceptanceTest extends AcceptanceTest {
    
    @MockBean
    OAuthService OAuthService;
    
    @Nested
    class 로그인 {
        
        @Nested
        class 카카오 {
            
            @Nested
            class 성공 {
                
                /**
                 * When 클라이언트는 카카오로부터 전달받은 정보로 로그인을 요청한다.
                 * Then 회원 가입 처리 후 토큰이 발급된다.
                 */
                @Test
                void 가입되지_않은_회원에게_토큰_발급() {
                    // given
                    when(OAuthService.authenticate(any(String.class), any(AuthorizationCodeRequest.class)))
                        .thenReturn(new Tokens("refresh-token", "access-token"));
                    
                    var 로그인_요청_정보 = new AuthorizationCodeRequest(라이언.인가_코드);
                    
                    // when
                    var 카카오_로그인_요청_응답 = 소셜_로그인_요청(로그인_요청_정보, OAuthProviderType.KAKAO);
                    
                    // then
                    토큰_확인(카카오_로그인_요청_응답);
                }
            }
            
            @Nested
            class 실패 {
                
                /**
                 * When  클라이언트는 카카오로부터 발급받은 인가 코드를 포함하지 않고 로그인을 요청한다.
                 * Then  회원 가입이 진행되지 않고, 토큰이 발급되지 않는다.
                 */
                @Test
                void 인가_코드를_전달하지_않은_경우() {
                    // when
                    실패하는_소셜_로그인_요청(OAuthProviderType.KAKAO);
                }
            }
        }
        
        @Nested
        class 네이버 {
            
            @Nested
            class 성공 {
                
                /**
                 * When 클라이언트는 네이버로부터 전달받은 정보로 로그인을 요청한다.
                 * Then 회원 가입 처리 후 토큰이 발급된다.
                 */
                @Test
                void 가입되지_않은_회원에게_토큰_발급() {
                    // given
                    when(OAuthService.authenticate(any(String.class), any(AuthorizationCodeRequest.class)))
                        .thenReturn(new Tokens("refresh-token", "access-token"));
                    
                    var 로그인_요청_정보 = new AuthorizationCodeRequest(나나.인가_코드);
                    
                    // when
                    var 네이버_로그인_요청_응답 = 소셜_로그인_요청(로그인_요청_정보, OAuthProviderType.NAVER);
                    
                    // then
                    토큰_확인(네이버_로그인_요청_응답);
                }
            }
            
            @Nested
            class 실패 {
                
                /**
                 * When  클라이언트는 네이버로부터 발급받은 인가 코드를 포함하지 않고 로그인을 요청한다.
                 * Then  회원 가입이 진행되지 않고, 토큰이 발급되지 않는다.
                 */
                @Test
                void 인가_코드를_전달하지_않은_경우() {
                    // when
                    실패하는_소셜_로그인_요청(OAuthProviderType.NAVER);
                }
                
            }
        }
        
    }
    
    @Nested
    class 로그아웃 {
        
        @Nested
        class 성공 {
            
            /**
             * Given 클라이언트는 카카오 로그인을 한다.
             * When  리프레시 토큰을 포함해서 로그아웃을 요청한다.
             * Then  정상적으로 로그아웃된다.
             */
            @Test
            void 로그아웃_처리() {
                // given
                when(OAuthService.authenticate(any(String.class), any(AuthorizationCodeRequest.class)))
                    .thenReturn(new Tokens("refresh-token", "access-token"));
                
                var 로그인_요청_정보 = new AuthorizationCodeRequest(라이언.인가_코드);
                var 카카오_로그인_요청_응답 = 소셜_로그인_요청(로그인_요청_정보, OAuthProviderType.KAKAO);
                var 발급된_리프레시_토큰 = 카카오_로그인_요청_응답.cookie("refresh-token");
                
                // when, then TODO: 추가 검증 방법 고민
                로그아웃_요청(발급된_리프레시_토큰);
            }
        }
        
        @Nested
        class 실패 {
            
            /**
             * When  리프레시 토큰없이 로그아웃을 요청한다.
             * Then  로그아웃에 실패한다.
             */
            @Test
            void 로그아웃_처리() {
                // when, then TODO: 검증 방법 고민
                실패하는_로그아웃_요청();
            }
        }
    }
    
}
