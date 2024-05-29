package com.techbloghub.core.post.application;

import static com.techbloghub.core.blog.company.domain.Company.우아한형제들;
import static org.assertj.core.api.Assertions.assertThat;

import com.techbloghub.common.util.ApplicationTest;
import com.techbloghub.core.blog.post.application.PostService;
import com.techbloghub.core.blog.post.domain.Post;
import com.techbloghub.core.blog.post.domain.PostRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("게시글 서비스 레이어 테스트")
public class PostServiceTest extends ApplicationTest {

  @Autowired
  PostService postService;

  @Autowired
  PostRepository postRepository;

  @Nested
  class 게시글_등록 {

    @Test
    void 게시글_등록_성공() {
      // given
      Post 우형_게시글 = new Post(우아한형제들,
          "https://techblog.woowahan.com/17713/",
          "[신청 시작] 6월 우아한테크세미나 : 글로벌 개발자로 성장하는 법",
          LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

      // when
      postService.registerPost(우형_게시글);

      // then
      List<Post> 모든_게시글 = postRepository.findAll();
      assertThat(모든_게시글).usingRecursiveComparison()
          .ignoringFields("id", "createdAt", "modifiedAt")
          .isEqualTo(List.of(우형_게시글));
    }
  }
}
