package com.techbloghub.core.post.acceptance;

import com.techbloghub.common.util.AcceptanceTest;
import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.post.domain.Post;
import com.techbloghub.core.post.domain.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.techbloghub.core.blog.domain.Blog.우아한형제들;
import static com.techbloghub.core.post.step.PostSteps.*;
import static com.techbloghub.core.post.util.PostTestUtil.게시글_더미_데이터_생성;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게시글 인수 테스트")
public class PostAcceptanceTest extends AcceptanceTest {

    @Autowired
    PostRepository postRepository;

    @Nested
    class 게시글_목록 {

        /**
         * given  게시글 더미 데이터를 생성한다.
         * When  조건 없이 게시글 목록을 요청할 경우
         * Then  기본 설정된 조건으로 게시글 목록이 응답된다.
         */
        @Test
        void 페이지_정보_없이_게시글_목록_요청() {
            // given
            var 생성된_게시글_데이터 = 게시글_더미_데이터_생성하기(우아한형제들, 20);

            var 예상하는_응답된_게시글_제목_목록 = 생성된_게시글_데이터.stream()
                    .sorted(Comparator.comparing(Post::getPublishAt).reversed())
                    .limit(페이지_기본_사이즈)
                    .map(Post::getTitle)
                    .collect(Collectors.toList());

            // when
            var 응답된_게시글_목록 = 게시글_목록_요청();

            // then
            var 응답된_게시글_제목_목록 = 게시글_제목만_분류하기(응답된_게시글_목록);
            assertThat(응답된_게시글_제목_목록).isEqualTo(예상하는_응답된_게시글_제목_목록);
        }


        /**
         * given  게시글 더미 데이터를 생성한다.
         * When  특정 조건을 포함해서 게시글 목록을 요청할 경우
         * Then  특정 조건을 기반으로 게시글 목록이 응답된다.
         */
        @Test
        void 페이지_정보를_포함한_게시글_목록_요청() {
            // given
            var 생성된_게시글_데이터 = 게시글_더미_데이터_생성하기(우아한형제들, 100);

            var 페이지_사이즈 = 5;
            var 페이지_번호 = 1;
            var 정렬_정보 = "publishAt,desc";

            var 예상하는_응답된_게시글_제목_목록 = 생성된_게시글_데이터.stream()
                    .sorted(Comparator.comparing(Post::getPublishAt).reversed())
                    .skip(5)
                    .limit(페이지_사이즈)
                    .map(Post::getTitle)
                    .collect(Collectors.toList());

            // when
            var 응답된_게시글_목록 = 게시글_목록_요청(페이지_사이즈, 페이지_번호, 정렬_정보);

            // then
            var 응답된_게시글_제목_목록 = 게시글_제목만_분류하기(응답된_게시글_목록);
            assertThat(응답된_게시글_제목_목록).isEqualTo(예상하는_응답된_게시글_제목_목록);
        }

        private List<Post> 게시글_더미_데이터_생성하기(Blog blog, int 개수) {
            List<Post> 생성된_게시글_데이터 = 게시글_더미_데이터_생성(blog, 개수);
            return postRepository.saveAll(생성된_게시글_데이터);
        }
    }
}
