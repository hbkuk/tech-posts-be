package com.techbloghub.core.post.domain;

import static com.techbloghub.core.post.domain.QPost.post;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.techbloghub.common.domain.pagination.CursorPaged;
import com.techbloghub.core.blog.domain.Blog;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {
    
    private final JPAQueryFactory queryFactory;
    
    /**
     * 주어진 검색 조건에 따라 게시글을 커서 기반 페이지네이션 조회 후 반환
     *
     * @param condition 게시글 검색 조건
     * @return 커서 기반으로 페이징된 게시글 목록
     */
    @Override
    public CursorPaged<Post> findAllPostsByCondition(PostSearchCondition condition) {
        List<Post> posts = queryFactory
            .selectFrom(post)
            .where(
                customCursor(condition.getCursor(), condition.getSort())
            )
            .orderBy(getOrderSpecifier(condition.getSort()))
            .limit(condition.getItemsPerPage() + 1)  // 한 페이지에 더 많은 항목 가져옴.
            .fetch();
        
        boolean hasMoreItems = posts.size() > condition.getItemsPerPage();
        
        if (hasMoreItems) {
            posts = posts.subList(0, condition.getItemsPerPage());
        }
        
        return new CursorPaged<>(posts, condition.getItemsPerPage(), hasMoreItems);
    }
    
    @Override
    public Optional<LocalDateTime> findLatestPublishDate(Blog blog) {
        LocalDateTime latestPublishDate = queryFactory
            .select(post.publishAt)
            .from(post)
            .where(post.blog.eq(blog))
            .orderBy(post.publishAt.desc())
            .limit(1)
            .fetchOne();
        
        return Optional.ofNullable(latestPublishDate);
    }
    
    /**
     * 주어진 정렬 조건에 따라 OrderSpecifier 생성
     *
     * @param sort 정렬 조건
     * @return 생성된 OrderSpecifier
     */
    private OrderSpecifier<?> getOrderSpecifier(Sort sort) {
        if (sort == Sort.LATEST) {
            return post.publishAt.desc();
        } else if (sort == Sort.OLDEST) {
            return post.publishAt.asc();
        }
        return post.publishAt.desc();
    }
    
    /**
     * 주어진 발행일과 게시글 ID를 기반으로 커서 생성 후 반환
     *
     * @param publishDate 게시글의 발행일
     * @param postId 게시글 ID
     * @return 생성된 커서 문자열
     */
    public String generateCustomCursor(LocalDateTime publishDate, Long postId) {
        if (publishDate == null && postId == null) { // 첫 페이지 조회를 위한 null 처리
            return null;
        }
        
        String customCursorPublishDate;
        String customCursorId;
        
        customCursorPublishDate = publishDate.toString()       // LocalDateTime을 문자열로 변환
            .replaceAll("T", "")
            .replaceAll("-", "")
            .replaceAll(":", "") + "00";    // 나노초를 00으로 고정
        
        customCursorPublishDate = String.format("%1$" + 20 + "s", customCursorPublishDate)
            .replace(' ', '0'); // 문자열 길이를 20자로 맞추고 남는 부분을 0으로 채움
        
        customCursorId = String.format("%1$" + 10 + "s", postId)
            .replace(' ', '0'); // 문자열 길이를 10자로 맞추고 남는 부분을 0으로 채움
        
        return customCursorPublishDate + customCursorId; // 발행일과 ID를 결합하여 커서 생성
    }
    
    private BooleanExpression customCursor(String cursor, Sort sort) {
        if (cursor == null) {
            return null;
        }
        
        StringExpression dateString = Expressions.stringTemplate(
            "CONCAT({0}, {1}, {2}, {3}, {4}, {5})",
            StringExpressions.lpad(post.publishAt.year().stringValue(), 4, '0'),
            StringExpressions.lpad(post.publishAt.month().stringValue(), 2, '0'),
            StringExpressions.lpad(post.publishAt.dayOfMonth().stringValue(), 2, '0'),
            StringExpressions.lpad(post.publishAt.hour().stringValue(), 2, '0'),
            StringExpressions.lpad(post.publishAt.minute().stringValue(), 2, '0'),
            StringExpressions.lpad(post.publishAt.second().stringValue(), 2, '0')
        );
        
        // 게시글 ID와 날짜 문자열 결합
        StringExpression cursorValue = StringExpressions.lpad(dateString, 20, '0')
            .concat(StringExpressions.lpad(post.id.stringValue(), 10, '0'));
        
        // 정렬 방향에 따라 비교 연산자 결정
        if (sort == Sort.LATEST) {
            return cursorValue.lt(cursor); // 최신 게시글 기준으로 비교
        } else if (sort == Sort.OLDEST) {
            return cursorValue.gt(cursor); // 오래된 게시글 기준으로 비교
        }
        
        // 기본적으로 최신 게시글 기준으로 비교
        return cursorValue.gt(cursor);
    }
}

