package com.techbloghub.core.post.acceptance;

import static com.techbloghub.core.blog.domain.Blog.우아한형제들;
import static com.techbloghub.core.post.step.PostSteps.게시글_목록_요청;
import static com.techbloghub.core.post.step.PostSteps.게시글_목록_확인;
import static com.techbloghub.core.post.util.PostTestUtil.게시글_테스트_데이터_생성하기;

import com.techbloghub.common.domain.pagination.PaginationRequest;
import com.techbloghub.common.util.AcceptanceTest;
import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.post.domain.Post;
import com.techbloghub.core.post.domain.PostRepository;
import com.techbloghub.core.post.step.PostSteps;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("게시글 인수 테스트")
public class PostAcceptanceTest extends AcceptanceTest {

    @Autowired
    PostRepository postRepository;

    @Nested
    class 게시글_목록 {

        /**
         * given  게시글을 등록한다.
         * When   아무 조건 없이 게시글 목록을 요청한다.
         * Then   기본 설정된 조건으로 선택된 게시글 목록이 응답된다.
         */
        @Test
        void 아무_조건없이_게시글_목록_요청() {
            // given
            var 저장된_게시글_목록 = 개수만큼_게시글_등록(우아한형제들, 20);

            // when
            var 응답된_게시글_목록 = 게시글_목록_요청();

            // then
            게시글_목록_확인(저장된_게시글_목록, 응답된_게시글_목록);
        }

        /**
         * given  게시글을 등록한다.
         * And    게시글 목록 요청과 관련된 조건을 설정한다.
         * When   특정 조건으로 게시글 목록을 요청한다.
         * Then   특정 조건으로 선택된 게시글 목록이 응답된다.
         */
        @Test
        void 페이지_정보를_포함한_게시글_목록_요청() {
            // given
            var 저장된_게시글_목록 = 개수만큼_게시글_등록(우아한형제들, 100);
            var 페이지_요청_정보 = new PaginationRequest(1, 5, "publishAt", "DESC");

            // when
            var 응답된_게시글_목록 = PostSteps.게시글_목록_요청(페이지_요청_정보);

            // then
            게시글_목록_확인(페이지_요청_정보, 저장된_게시글_목록, 응답된_게시글_목록);
        }

        private List<Post> 개수만큼_게시글_등록(Blog 블로그, int 개수) {
            List<Post> 생성된_게시글_데이터 = 게시글_테스트_데이터_생성하기(블로그, 개수);
            return postRepository.saveAll(생성된_게시글_데이터);
        }
    }
}
