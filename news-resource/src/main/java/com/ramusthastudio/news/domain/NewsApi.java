package com.ramusthastudio.news.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "news_api")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NewsApi {
  @Id
  @GeneratedValue
  private Long id;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String newsId;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String channelTitle;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String channelDescription;
  @Temporal(TemporalType.TIMESTAMP)
  private Date channelPubDate;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String channelImageTitle;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String channelImageLink;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String channelImageUrl;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String channelName;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String title;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String link;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String category;
  @Temporal(TemporalType.TIMESTAMP)
  private Date pubDate;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String creator;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String description;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  @ElementCollection
  private List<String> media;
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String fullContent;
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdTime;
}
