package com.techbloghub.core.post.domain;

import com.techbloghub.common.domain.base.BaseEntity;
import com.techbloghub.core.blog.domain.Blog;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Blog blog;

    private String link;

    private String title;

    private LocalDateTime publishAt;

    private String description;

    public Post(Blog blog, String link, String title, LocalDateTime publishAt, String description) {
        this.blog = blog;
        this.link = link;
        this.title = title;
        this.publishAt = publishAt;
        this.description = description;
    }
}
