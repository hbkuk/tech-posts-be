package com.techbloghub.core.post.domain;

import com.techbloghub.core.blog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT post FROM Post post " +
        "WHERE post.blog = :blog " +
        "AND post.publishAt = (SELECT MAX(p.publishAt) " +
        "FROM Post p " +
        "WHERE p.blog = :blog) " +
        "AND post.id = (SELECT MAX(p2.id) " +
        "FROM Post p2 " +
        "WHERE p2.blog = :blog " +
        "AND p2.publishAt = post.publishAt)")
    Optional<Post> findLatestPost(@Param("blog") Blog blog);

}
