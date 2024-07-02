package com.techbloghub.core.post.application;

import com.techbloghub.common.domain.pagination.PagedResponse;
import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.post.application.dto.PostCreateRequest;
import com.techbloghub.core.post.domain.Post;
import com.techbloghub.core.post.domain.PostRepository;
import com.techbloghub.core.post.presentation.dto.PostResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "post", key = "#request.pageNumber + '-' + #request.pageSize + '-' + #request.sort.toString()")
    public PagedResponse<PostResponse> findAll(Pageable request) {
        // TODO: PaginationRequest 유효성 검증 필요
        Page<Post> posts = postRepository.findAll(request);
        return convertPagedResponse(mapPostResponse(posts), posts);
    }

    @Transactional
    public void registerPost(Post post) {
        postRepository.save(post);
    }

    @Transactional
    public void registerPost(List<PostCreateRequest> requests) {
        requests.stream()
            .map(request -> new Post(
                request.getBlog(),
                request.getLink(),
                request.getTitle(),
                request.getPostedAt(),
                request.getDescription()))
            .toList()
            .forEach(this::registerPost);
    }

    private List<PostResponse> mapPostResponse(Page<Post> posts) {
        return posts.stream()
            .map(PostResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<LocalDateTime> getLatestPublishDate(Blog blog) {
        Optional<Post> latestPost = postRepository.findLatestPost(blog);
        return latestPost.map(Post::getPublishAt);
    }

    private PagedResponse<PostResponse> convertPagedResponse(
        List<PostResponse> postResponses, Page<Post> posts) {
        return new PagedResponse<>(
            postResponses,
            posts.getNumber(),
            posts.getTotalPages(),
            posts.getTotalElements(),
            posts.getSize()
        );
    }

}
