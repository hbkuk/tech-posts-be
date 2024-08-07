package com.techbloghub.core.post.domain;

import static com.techbloghub.core.blog.domain.Blog.우아한형제들;
import static com.techbloghub.core.post.util.PostTestUtil.게시글_테스트_데이터_생성하기;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.techbloghub.common.util.RepositoryTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@DisplayName("게시글 Repository 테스트")
public class PostRepositoryTest extends RepositoryTest {
    
    @Autowired
    PostRepository postRepository;
    
    @Test
    void 페이지_정보() {
        // given
        List<Post> 생성된_게시글_목록 = 게시글_테스트_데이터_생성하기(우아한형제들, 31);
        postRepository.saveAll(생성된_게시글_목록);
        
        // when
        PageRequest 페이지_정보 = PageRequest.of(2, 10, Sort.Direction.DESC, "id");
        Page<Post> posts = postRepository.findAll(페이지_정보);
        
        // then
        assertAll(
            () -> assertThat(posts.getNumber()).isEqualTo(2),
            () -> assertThat(posts.getTotalPages()).isEqualTo(4),
            () -> assertThat(posts.getTotalElements()).isEqualTo(31),
            () -> assertThat(posts.getSize()).isEqualTo(10)
        );
    }
    
}
