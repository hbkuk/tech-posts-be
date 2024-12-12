package com.techbloghub.core.post.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.techbloghub.common.domain.pagination.CursorPaged;
import com.techbloghub.core.post.application.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@DisplayName("Post Repository 데이터 로드 상태 테스트")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostRepositoryOnLoadDataTest {
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private PostQueryRepositoryImpl postQueryRepository;
    
    @Test
    @Sql(scripts = {"classpath:database-script/post-insert.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void 커서_기반_페이지네이션_연속_조회() {
        // Given
        int 페이지_당_개수 = 15;
        
        // When & Then
        CursorPaged<Post> 첫번째_페이지_결과 = 페이지_요청(null, 페이지_당_개수, true);
        CursorPaged<Post> 두번째_페이지_결과 = 페이지_요청(페이지_정보로_커서_생성(첫번째_페이지_결과), 페이지_당_개수, true);
        CursorPaged<Post> 세번째_페이지_결과 = 페이지_요청(페이지_정보로_커서_생성(두번째_페이지_결과), 페이지_당_개수, true);
        
        페이지_검증(첫번째_페이지_결과, 두번째_페이지_결과);
        페이지_검증(두번째_페이지_결과, 세번째_페이지_결과);
    }
    
    // TODO.Test - Full Text Search
    
    private CursorPaged<Post> 페이지_요청(String cursor, int itemsPerPage, boolean expectedHasMore) {
        PostSearchCondition condition = PostSearchCondition.builder()
            .cursor(cursor)
            .sort(Sort.LATEST)
            .itemsPerPage(itemsPerPage)
            .build();
        
        CursorPaged<Post> result = postService.findAllByCondition(condition);
        
        assertAll(
            () -> assertThat(result.getItems()).hasSize(itemsPerPage),
            () -> assertThat(result.isHasMoreItems()).isEqualTo(expectedHasMore)
        );
        
        return result;
    }
    
    private String 페이지_정보로_커서_생성(CursorPaged<Post> page) {
        Post 마지막_페이지 = page.getItems().get(page.getItems().size() - 1);
        return postQueryRepository.generateCustomCursor(마지막_페이지.getPublishAt(), 마지막_페이지.getId());
    }
    
    private void 페이지_검증(CursorPaged<Post> 이전_페이지_결과, CursorPaged<Post> 현재_페이지_결과) {
        Post 이전_페이지의_마지막_게시글 = 이전_페이지_결과.getItems().get(이전_페이지_결과.getItems().size() - 1);
        Post 현재_페이지의_첫번째_게시글 = 현재_페이지_결과.getItems().get(0);
        
        assertThat(이전_페이지의_마지막_게시글.getPublishAt()).isAfter(현재_페이지의_첫번째_게시글.getPublishAt());
    }
}