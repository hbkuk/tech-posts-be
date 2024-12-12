package com.techbloghub.core.authentication.application;

import static com.techbloghub.core.authentication.fixture.KakaoMemberFixture.모든_카카오_회원_가져오기;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.techbloghub.common.ApplicationTest;
import com.techbloghub.common.util.WebClientUtil;
import com.techbloghub.core.authentication.fixture.KakaoMemberFixture;
import com.techbloghub.core.authentication.application.kakao.KakaoOauthProvider;
import com.techbloghub.core.authentication.application.kakao.dto.KakaoAccessTokenResponse;
import com.techbloghub.core.authentication.application.kakao.dto.KakaoProfileResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

@DisplayName("Kakao Oauth Provider Mock 테스트")
public class KakaoOauthProviderMockTest extends ApplicationTest {
    
    @Autowired
    KakaoOauthProvider kakaoOauthProvider;
    
    @MockBean
    WebClientUtil webClientUtil;
    
    @Nested
    class 카카오_인가_코드로_카카오_프로필_요청 {
        
        @Test
        void 성공() {
            모든_카카오_회원_가져오기().forEach(카카오_회원 -> {
                // given
                KakaoAccessTokenResponse 카카오_토큰_응답 = new KakaoAccessTokenResponse(
                    "bearer",
                    KakaoMemberFixture.findTokenByCode(카카오_회원.인가_코드));
                when(webClientUtil.post(
                    any(String.class),
                    any(MultiValueMap.class),
                    any(MediaType.class),
                    eq(KakaoAccessTokenResponse.class)
                )).thenReturn(Mono.just(카카오_토큰_응답));
                
                Long 아이디 = KakaoMemberFixture.findIdByToken(카카오_토큰_응답.getAccessToken());
                when(webClientUtil.get(
                    any(String.class),
                    any(MultiValueMap.class),
                    eq(KakaoProfileResponse.class)
                )).thenReturn(Mono.just(new KakaoProfileResponse(아이디)));
                
                // when
                OauthUserProfile 카카오에서_발급한_토큰
                    = kakaoOauthProvider.getUserProfile(카카오_회원.인가_코드);
                
                // then
                assertThat(카카오에서_발급한_토큰.getSocialId()).isEqualTo(카카오_회원.카카오_회원_번호.toString());
            });
            
        }
    }
}
