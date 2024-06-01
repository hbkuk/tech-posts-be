package com.techbloghub.core.post.domain;

import com.techbloghub.common.domain.base.BaseEntity;
import com.techbloghub.core.blog.domain.Blog;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
