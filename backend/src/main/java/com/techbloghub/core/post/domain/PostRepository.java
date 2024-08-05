package com.techbloghub.core.post.domain;

import com.techbloghub.core.blog.domain.Blog;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    @Query("SELECT post.publishAt " +
        "FROM Post post " +
        "WHERE post.blog = :blog " +
        "ORDER BY post.publishAt DESC " +
        "LIMIT 1")
    Optional<LocalDateTime> findLatestPublishDate(@Param("blog") Blog blog);


}
