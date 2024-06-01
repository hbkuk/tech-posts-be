package com.techbloghub.core.post.application;

import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.post.application.dto.PostCreateRequest;
import com.techbloghub.core.post.domain.Post;
import com.techbloghub.core.post.domain.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void registerPost(Post post) {
        postRepository.save(post);
    }

    public void registerPost(List<PostCreateRequest> requests) {
        requests.stream()
                .map(request -> new Post(
                        request.getBlog(),
                        request.getLink(),
                        request.getTitle(),
                        request.getPostedAt(),
                        request.getDescription()))
                .collect(Collectors.toList())
                .forEach(this::registerPost);
    }

    public Optional<LocalDateTime> getLatestPublishDate(Blog blog) {
        Optional<Post> latestPost = postRepository.findLatestPost(blog);
        return latestPost.map(Post::getPublishAt);
    }
}
