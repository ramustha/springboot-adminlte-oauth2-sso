package com.ramusthastudio.news.internal.domain;

import java.time.Instant;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class News {
  private String id;
  private String channelTitle;
  private String channelDescription;
  private Instant channelPubDate;
  private String channelImageTitle;
  private String channelImageLink;
  private String channelImageUrl;
  private String channelName;
  private String title;
  private String link;
  private String category;
  private Instant pubDate;
  private String creator;
  private String description;
  private List<String> media;
  private String fullContent;
  private Instant createdTime;
}
