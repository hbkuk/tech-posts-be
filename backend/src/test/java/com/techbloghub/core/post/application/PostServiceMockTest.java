package com.techbloghub.core.post.application;

import static com.techbloghub.core.blog.domain.Blog.우아한형제들;
import static com.techbloghub.core.post.util.PostTestUtil.게시글_테스트_데이터_생성하기;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.techbloghub.common.domain.pagination.CursorPaged;
import com.techbloghub.common.util.ApplicationTest;
import com.techbloghub.core.post.domain.Post;
import com.techbloghub.core.post.domain.PostRepositoryImpl;
import com.techbloghub.core.post.domain.PostSearchCondition;
import com.techbloghub.core.post.domain.Sort;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // 테스트 메서드간 캐싱 동작으로 인한 Spring 컨테이너 초기화
@DisplayName("게시글 서비스 Mock 테스트")
public class PostServiceMockTest extends ApplicationTest {

    @Autowired
    PostService postService;

    @MockBean
    PostRepositoryImpl postRepository;

    @Test
    void 검색_조건에_따른_게시글_목록_캐싱_확인() {
        // given
        List<Post> 생성된_게시글_목록 = 게시글_테스트_데이터_생성하기(우아한형제들, 10);
        PostSearchCondition 검색_조건 = PostSearchCondition.builder()
            .cursor("000000202403151030000000012345")
            .sort(Sort.LATEST)
            .itemsPerPage(10)
            .build();
        
        when(postRepository.findAllPostsByCondition(검색_조건)).thenReturn(new CursorPaged<>(생성된_게시글_목록, 10, true));

        // when
        
        IntStream.range(0, 10).forEach((count) -> postService.findAllByCondition(검색_조건));

        // then
        verify(postRepository, times(1)).findAllPostsByCondition(검색_조건);
    }
    
    @Test
    void 검색_조건이_다른_경우_게시글_목록_조회_요청은_캐싱되지_않는다() {
        // given
        List<Post> 생성된_게시글_목록 = 게시글_테스트_데이터_생성하기(우아한형제들, 10);
        PostSearchCondition 첫번째_검색_조건 = PostSearchCondition.builder()
            .cursor("000000202403151030000000012345")
            .sort(Sort.LATEST)
            .itemsPerPage(10)
            .build();
        
        PostSearchCondition 두번째_검색_조건 = PostSearchCondition.builder()
            .cursor("000000202403151030000000012345")
            .sort(Sort.OLDEST)
            .itemsPerPage(15)
            .build();
        
        // Mocking repository behavior
        List<Post> 정렬된_게시글_목록_최신순 = 생성된_게시글_목록.stream()
            .sorted(Comparator.comparing(Post::getPublishAt).reversed())
            .collect(Collectors.toList());
        
        List<Post> 정렬된_게시글_목록_오래된순 = 생성된_게시글_목록.stream()
            .sorted(Comparator.comparing(Post::getPublishAt))
            .collect(Collectors.toList());
        
        when(postRepository.findAllPostsByCondition(첫번째_검색_조건)).thenReturn(new CursorPaged<>(정렬된_게시글_목록_최신순, 10, true));
        when(postRepository.findAllPostsByCondition(두번째_검색_조건)).thenReturn(new CursorPaged<>(정렬된_게시글_목록_오래된순, 15, true));
        
        // when
        IntStream.range(0, 10).forEach((count) -> {
            postService.findAllByCondition(첫번째_검색_조건);
            postService.findAllByCondition(두번째_검색_조건);
        });
        
        // then
        verify(postRepository, times(1)).findAllPostsByCondition(첫번째_검색_조건);
        verify(postRepository, times(1)).findAllPostsByCondition(두번째_검색_조건);
    }
}
