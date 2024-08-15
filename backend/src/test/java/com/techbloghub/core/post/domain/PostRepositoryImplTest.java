package com.techbloghub.core.post.domain;

import static com.techbloghub.core.blog.domain.Blog.라인;
import static com.techbloghub.core.post.util.PostTestUtil.게시글_테스트_데이터_생성하기;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.techbloghub.common.domain.pagination.CursorPaged;
import com.techbloghub.common.util.RepositoryTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("게시글 Repository 테스트")
public class PostRepositoryImplTest extends RepositoryTest {
    
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    PostRepositoryImpl postRepositoryImpl;
    
    @Test
    void null_조건으로_커서_생성() {
        // given
        LocalDateTime 발행_일자 = null;
        Long 게시글_번호 = null;
        
        // when
        String 커서 = postRepositoryImpl.generateCustomCursor(발행_일자, 게시글_번호);
        
        // then
        assertThat(커서).isNull();
    }
    
    @Test
    void 커서_생성() {
        // given
        LocalDateTime 발행_일자 = LocalDateTime.of(2024, 3, 15, 10, 30, 0);
        Long 게시글_번호 = 12345L;
        
        // when
        String 커서 = postRepositoryImpl.generateCustomCursor(발행_일자, 게시글_번호);
        
        // then
        assertThat(커서).isNotNull();
        assertThat(커서).hasSize(30);  // 20(날짜) + 10(ID)
        assertThat(커서).isEqualTo("000000202403151030000000012345");
    }
    
    @Test
    void 커서를_이용한_게시글_조회() {
        // given
        List<Post> 생성된_게시글_목록 = 게시글_테스트_데이터_생성하기(라인, 3);
        postRepository.saveAll(생성된_게시글_목록);
        
        PostSearchCondition 첫번째_검색의_검색_조건 = PostSearchCondition.builder()
            .sort(Sort.LATEST)
            .itemsPerPage(2)
            .build();
        
        // when
        CursorPaged<Post> 첫번째_검색의_검색_결과 = postRepository.findAllPostsByCondition(첫번째_검색의_검색_조건);
        
        // then
        assertThat(첫번째_검색의_검색_결과.getItems()).hasSize(2);
        assertThat(첫번째_검색의_검색_결과.isHasMoreItems()).isTrue();
        
        // 다음 페이지 조회
        String 커서 = postRepositoryImpl.generateCustomCursor(
            첫번째_검색의_검색_결과.getItems().get(첫번째_검색의_검색_결과.getItems().size() - 1).getPublishAt(),
            첫번째_검색의_검색_결과.getItems().get(첫번째_검색의_검색_결과.getItems().size() - 1).getId()
        );
        
        PostSearchCondition 두번째_검색의_검색_조건 = PostSearchCondition.builder()
            .cursor(커서)
            .sort(Sort.LATEST)
            .itemsPerPage(2)
            .build();
        
        
        CursorPaged<Post> 두번째_검색의_검색_결과 = postRepository.findAllPostsByCondition(두번째_검색의_검색_조건);
        
        assertThat(두번째_검색의_검색_결과.getItems()).hasSize(1);
        assertThat(두번째_검색의_검색_결과.isHasMoreItems()).isFalse();
    }
}
