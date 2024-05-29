package com.techbloghub.core.blog.post.application;

import com.techbloghub.core.blog.post.domain.Post;
import com.techbloghub.core.blog.post.domain.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public void registerPost(Post post) {
    postRepository.save(post);
  }
}
