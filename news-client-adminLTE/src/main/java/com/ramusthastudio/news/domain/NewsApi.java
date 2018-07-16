package com.ramusthastudio.news.domain;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class NewsApi {
  private Long id;
  private String newsId;
  private String channelTitle;
  private String channelDescription;
  private Date channelPubDate;
  private String channelImageTitle;
  private String channelImageLink;
  private String channelImageUrl;
  private String channelName;
  private String title;
  private String link;
  private String category;
  private Date pubDate;
  private String creator;
  private String description;
  private List<String> media;
  private String fullContent;
  private Date createdTime;
}
