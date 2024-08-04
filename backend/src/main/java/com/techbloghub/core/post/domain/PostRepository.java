package com.techbloghub.core.post.domain;

import com.techbloghub.core.blog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    @Query("SELECT post.publishAt " +
        "FROM Post post " +
        "WHERE post.blog = :blog " +
        "ORDER BY post.publishAt DESC " +
        "LIMIT 1")
    Optional<Post> findLatestPost(@Param("blog") Blog blog);


}
