package com.techbloghub.core.post.domain;

import com.techbloghub.common.domain.base.BaseEntity;
import com.techbloghub.core.blog.domain.Blog;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

  private Blog blog;

  private String link;

  private String title;

  private LocalDateTime postedAt;

  public Post(Blog blog, String link, String title, LocalDateTime postedAt) {
    this.blog = blog;
    this.link = link;
    this.title = title;
    this.postedAt = postedAt;
  }
}
