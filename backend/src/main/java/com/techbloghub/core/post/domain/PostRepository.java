package com.techbloghub.core.post.domain;

import com.techbloghub.core.blog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    @Query("SELECT post FROM Post post " +
        "LEFT JOIN Post post2 ON post.blog = post2.blog " +
        "AND (post.publishAt < post2.publishAt OR (post.publishAt = post2.publishAt AND post.id < post2.id)) " +
        "WHERE post2.id IS NULL AND post.blog = :blog")
    Optional<Post> findLatestPost(@Param("blog") Blog blog);


}
