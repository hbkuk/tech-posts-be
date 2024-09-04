package com.techbloghub.core.post.domain;

import com.techbloghub.common.domain.base.BaseEntity;
import com.techbloghub.core.blog.domain.Blog;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(
    indexes = {
        @Index(name = "idx_post_covering", columnList = "publishAt DESC, id, blog, link, title")
    }
)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Blog blog;
    
    @Column(length = 500)
    private String link;
    
    @Column(length = 100)
    private String title;

    private LocalDateTime publishAt;
    
    public Post(Blog blog, String link, String title, LocalDateTime publishAt) {
        this.blog = blog;
        this.link = link;
        this.title = title;
        this.publishAt = publishAt;
    }
}
