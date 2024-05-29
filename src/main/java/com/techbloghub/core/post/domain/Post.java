package com.techbloghub.core.blog.post.domain;

import com.techbloghub.common.domain.base.BaseEntity;
import com.techbloghub.core.blog.company.domain.Company;
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

  private Company company;

  private String url;

  private String title;

  private LocalDateTime postedAt;

  public Post(Company company, String url, String title, LocalDateTime postedAt) {
    this.company = company;
    this.url = url;
    this.title = title;
    this.postedAt = postedAt;
  }
}
