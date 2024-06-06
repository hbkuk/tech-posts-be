package com.techbloghub.core.post.acceptance;

import com.techbloghub.common.util.AcceptanceTest;
import com.techbloghub.core.post.domain.Post;
import com.techbloghub.core.post.domain.PostRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.techbloghub.core.blog.domain.Blog.우아한형제들;
import static com.techbloghub.core.post.util.PostTestUtil.게시글_더미_데이터_생성;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게시글 인수 테스트")
public class PostAcceptanceTest extends AcceptanceTest {

    public static final int PAGEABLE_PAGE_DEFAULT_SIZE = 10;

    @Autowired
    PostRepository postRepository;

    @Nested
    class 게시글_목록 {

        /**
         * When  페이지 정보없이 게시글 목록을 요청할 경우
         * Then  최신순으로 게시글 목록이 응답된다.
         */
        @Test
        void 페이지_정보_없이_게시글_목록_요청() {
            // given
            List<Post> 생성된_게시글_데이터 = 게시글_더미_데이터_생성(우아한형제들, 20);
            postRepository.saveAll(생성된_게시글_데이터);

            List<String> 저장된_게시글의_타이틀_목록 = 생성된_게시글_데이터.stream()
                    .sorted(Comparator.comparing(Post::getPublishAt).reversed())
                    .limit(PAGEABLE_PAGE_DEFAULT_SIZE)
                    .map(Post::getTitle)
                    .collect(Collectors.toList());

            // when
            ExtractableResponse<Response> 게시글_목록_응답 = given().log().all()
                    .when().log().all()
                    .get("/posts")
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value())
                    .extract();

            // then
            List<String> 게시글의_타이틀_목록 = 게시글_목록_응답.jsonPath().getList("title", String.class);
            assertThat(게시글의_타이틀_목록).isEqualTo(저장된_게시글의_타이틀_목록);
        }

        /**
         * When  페이지 정보를 포함해서 게시글 목록을 요청할 경우
         * Then  페이지 정보 기반으로 최신순으로 게시글 목록이 응답된다.
         */
        @Test
        void 페이지_정보를_포함한_게시글_목록_요청() {
            // given
            List<Post> 생성된_게시글_데이터 = 게시글_더미_데이터_생성(우아한형제들, 100);
            postRepository.saveAll(생성된_게시글_데이터);

            int size = 5;
            int page = 1;

            List<String> 저장된_게시글의_타이틀_목록 = 생성된_게시글_데이터.stream()
                    .sorted(Comparator.comparing(Post::getPublishAt).reversed())
                    .skip(5)
                    .limit(size)
                    .map(Post::getTitle)
                    .collect(Collectors.toList());

            // when
            Map<String, String> params = new HashMap<>();
            params.put("size", String.valueOf(size));
            params.put("page", String.valueOf(page));
            params.put("sort", "publishAt,desc");

            ExtractableResponse<Response> 게시글_목록_응답 = given().log().all()
                    .params(params)
                    .when().log().all()
                    .get("/posts")
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value())
                    .extract();

            // then
            List<String> 게시글의_타이틀_목록 = 게시글_목록_응답.jsonPath().getList("title", String.class);
            assertThat(게시글의_타이틀_목록).isEqualTo(저장된_게시글의_타이틀_목록);
        }

    }
}
