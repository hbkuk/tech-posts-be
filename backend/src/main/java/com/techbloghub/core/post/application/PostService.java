package com.techbloghub.core.post.application;

import com.techbloghub.common.domain.pagination.CursorPaged;
import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.post.application.dto.PostCreateRequest;
import com.techbloghub.core.post.domain.Post;
import com.techbloghub.core.post.domain.PostRepository;
import com.techbloghub.core.post.domain.PostSearchCondition;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    @Cacheable(
        cacheNames = "post",
        key = "#condition.sort.toString() + '-' + #condition.cursor + '-' + #condition.itemsPerPage + '-' + #condition.blog.getEnglishName()")
    public CursorPaged<Post> findAllByCondition(PostSearchCondition condition) {
        return postRepository.findAllPostsByCondition(condition);
    }

    @Transactional
    public void registerPost(Post post) {
        postRepository.save(post);
    }

    @Transactional
    public void registerPosts(List<PostCreateRequest> requests) {
        requests.stream()
            .map(request -> new Post(
                request.getBlog(),
                request.getLink(),
                request.getTitle(),
                request.getPostedAt()))
            .toList()
            .forEach(this::registerPost);
    }

    @Transactional(readOnly = true) // TODO: Cache 설정 필요 <-> 스케줄러 확인, Rss 업데이트 날짜와 비교
    public Optional<LocalDateTime> getLatestPublishDate(Blog blog) {
        return postRepository.findLatestPublishDate(blog);
    }
}
