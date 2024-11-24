package com.techbloghub.core.post.domain;

import static com.techbloghub.core.blog.domain.Blog.라인;
import static com.techbloghub.core.blog.domain.Blog.카카오;
import static com.techbloghub.core.post.util.PostTestUtil.게시글_테스트_데이터_생성하기;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.techbloghub.common.domain.pagination.CursorPaged;
import com.techbloghub.common.util.RepositoryTest;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("게시글 Repository 테스트")
public class PostRepositoryTest extends RepositoryTest {
    
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    PostQueryRepositoryImpl postRepositoryImpl;
    
    @Test
    void 최신순으로_게시글_목록_조회() {
        // given
        List<Post> 생성된_게시글_목록 = 게시글_테스트_데이터_생성하기(카카오, 20);
        postRepository.saveAll(생성된_게시글_목록);
        
        PostSearchCondition 검색_조건 = PostSearchCondition.builder()
            .sort(Sort.LATEST)
            .itemsPerPage(10)
            .build();
        
        // when
        CursorPaged<Post> 검색_결과 = postRepository.findAllPostsByCondition(검색_조건);
        
        // then
        assertAll(
            () -> assertThat(검색_결과.getItems()).hasSize(10),
            () -> assertThat(검색_결과.isHasMoreItems()).isTrue(),
            () -> assertThat(검색_결과.getItems()).isSortedAccordingTo(Comparator.comparing(Post::getPublishAt).reversed())
        );
    }
    
    @Test
    void 오래된순으로_게시글_목록_조회() {
        // given
        List<Post> 생성된_게시글_목록 = 게시글_테스트_데이터_생성하기(라인, 15);
        postRepository.saveAll(생성된_게시글_목록);
        
        PostSearchCondition 검색_조건 = PostSearchCondition.builder()
            .sort(Sort.OLDEST)
            .itemsPerPage(10)
            .build();
        
        // when
        CursorPaged<Post> 검색_결과 = postRepository.findAllPostsByCondition(검색_조건);
        
        // then
        assertAll(
            () -> assertThat(검색_결과.getItems()).hasSize(10),
            () -> assertThat(검색_결과.isHasMoreItems()).isTrue(),
            () -> assertThat(검색_결과.getItems()).isSortedAccordingTo(Comparator.comparing(Post::getPublishAt))
        );
    }

    @Test
    @DisplayName("커서를 이용한 다음 페이지 조회")
    void 커서를_이용한_다음_페이지_조회() {
        // given
        List<Post> 생성된_게시글_목록 = 게시글_테스트_데이터_생성하기(카카오, 25);
        postRepository.saveAll(생성된_게시글_목록);

        PostSearchCondition 첫_페이지_조건_정보 = PostSearchCondition.builder()
            .sort(Sort.LATEST)
            .itemsPerPage(10)
            .build();

        CursorPaged<Post> 첫_페이지_결과 = postRepository.findAllPostsByCondition(첫_페이지_조건_정보);
        String 커서 = postRepositoryImpl.generateCustomCursor(
            첫_페이지_결과.getItems().get(첫_페이지_결과.getItems().size() - 1).getPublishAt(),
            첫_페이지_결과.getItems().get(첫_페이지_결과.getItems().size() - 1).getId()
        );

        PostSearchCondition 다음_페이지_조건 = PostSearchCondition.builder()
            .sort(Sort.LATEST)
            .itemsPerPage(10)
            .cursor(커서)
            .build();

        // when
        CursorPaged<Post> 다음_페이지_결과 = postRepository.findAllPostsByCondition(다음_페이지_조건);

        // then
        assertAll(
            () -> assertThat(다음_페이지_결과.getItems()).hasSize(10),
            () -> assertThat(다음_페이지_결과.isHasMoreItems()).isTrue()
        );
    }

    @Test
    @DisplayName("마지막 페이지 조회")
    void 마지막_페이지_조회() {
        // given
        List<Post> 생성된_게시글_목록 = 게시글_테스트_데이터_생성하기(라인, 18);
        postRepository.saveAll(생성된_게시글_목록);

        PostSearchCondition 검색_조건 = PostSearchCondition.builder()
            .sort(Sort.LATEST)
            .itemsPerPage(10)
            .build();

        CursorPaged<Post> 첫_페이지_결과 = postRepository.findAllPostsByCondition(검색_조건);
        String 커서 = postRepositoryImpl.generateCustomCursor(
            첫_페이지_결과.getItems().get(첫_페이지_결과.getItems().size() - 1).getPublishAt(),
            첫_페이지_결과.getItems().get(첫_페이지_결과.getItems().size() - 1).getId()
        );

        PostSearchCondition 마지막_페이지_조건 = PostSearchCondition.builder()
            .sort(Sort.LATEST)
            .itemsPerPage(10)
            .cursor(커서)
            .build();

        // when
        CursorPaged<Post> 마지막_페이지_결과 = postRepository.findAllPostsByCondition(마지막_페이지_조건);

        // then
        assertAll(
            () -> assertThat(마지막_페이지_결과.getItems()).hasSize(8),
            () -> assertThat(마지막_페이지_결과.isHasMoreItems()).isFalse()
        );
    }
    
    @Test
    void 블로그로_게시글_목록_조회() {
        // given
        List<Post> 생성된_라인_게시글_목록 = 게시글_테스트_데이터_생성하기(라인, 15);
        postRepository.saveAll(생성된_라인_게시글_목록);
        
        List<Post> 생성된_카카오_게시글_목록 = 게시글_테스트_데이터_생성하기(카카오, 15);
        postRepository.saveAll(생성된_카카오_게시글_목록);
        
        PostSearchCondition 검색_조건 = PostSearchCondition.builder()
            .sort(Sort.LATEST)
            .itemsPerPage(10)
            .blog(카카오)
            .build();
        
        // when
        CursorPaged<Post> 검색_결과 = postRepository.findAllPostsByCondition(검색_조건);
        
        // then
        assertAll(
            () -> assertThat(검색_결과.getItems()).hasSize(10),
            () -> assertThat(검색_결과.isHasMoreItems()).isTrue(),
            () -> assertThat(검색_결과.getItems())
                .isSortedAccordingTo(Comparator.comparing(Post::getPublishAt).reversed()), // 최신순 정렬 검증
            () -> assertThat(검색_결과.getItems())
                .allMatch(post -> post.getBlog().equals(카카오)) // Blog 조건 검증
        );
    }
}
