package com.techbloghub.core.auth.application;

import static com.techbloghub.core.auth.fixture.NaverMemberFixture.모든_네이버_회원_가져오기;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.techbloghub.common.util.ApplicationTest;
import com.techbloghub.common.util.WebClientUtil;
import com.techbloghub.core.auth.fixture.NaverMemberFixture;
import com.techbloghub.core.authentication.application.OauthUserProfile;
import com.techbloghub.core.authentication.application.jwt.BearerAuthorizationExtractor;
import com.techbloghub.core.authentication.application.naver.NaverOauthProvider;
import com.techbloghub.core.authentication.application.naver.dto.NaverAccessTokenResponse;
import com.techbloghub.core.authentication.application.naver.dto.NaverProfileResponse;
import com.techbloghub.core.authentication.application.naver.dto.NaverProfileResponse.NaverUserDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

@DisplayName("Naver Oauth Provider Mock 테스트")
public class NaverOauthProviderMockTest extends ApplicationTest {
    
    @Autowired
    NaverOauthProvider naverOauthProvider;
    
    @MockBean
    WebClientUtil webClientUtil;
    
    @Nested
    class 네이버_인가_코드로_네이버_프로필_요청 {
        
        @Test
        void 성공() {
            모든_네이버_회원_가져오기().forEach(네이버_회원 -> {
                // given
                NaverAccessTokenResponse 네이버_토큰_응답 = new NaverAccessTokenResponse(
                    "bearer",
                    NaverMemberFixture.findTokenByCode(네이버_회원.인가_코드));
                when(webClientUtil.post(
                    any(String.class),
                    any(MultiValueMap.class),
                    any(MediaType.class),
                    eq(NaverAccessTokenResponse.class)
                )).thenReturn(Mono.just(네이버_토큰_응답));
                
                String 아이디 = NaverMemberFixture.findIdByToken(네이버_토큰_응답.getAccessToken());
                when(webClientUtil.get(
                    any(String.class),
                    any(MultiValueMap.class),
                    eq(NaverProfileResponse.class)
                )).thenReturn(Mono.just(new NaverProfileResponse("00", "success", new NaverUserDetail(아이디))));
                
                // when
                OauthUserProfile 네이버에서_발급한_토큰
                    = naverOauthProvider.getUserProfile(네이버_회원.인가_코드);
                
                // then
                assertThat(네이버에서_발급한_토큰.getSocialId()).isEqualTo(네이버_회원.네이버_회원_번호);
            });
            
        }
    }
}
