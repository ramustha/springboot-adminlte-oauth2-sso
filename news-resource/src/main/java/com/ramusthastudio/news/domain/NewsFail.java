package com.ramusthastudio.news.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "news_fail")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class NewsFail {
  @Id
  @GeneratedValue
  private Long id;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String newsId;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String link;
  @Temporal(TemporalType.TIMESTAMP)
  private Date pubDate;
  private String reason;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String reasonNote;
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdTime;
  @Column(columnDefinition = "boolean default false")
  private boolean fixed;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String fixedNote;
}
