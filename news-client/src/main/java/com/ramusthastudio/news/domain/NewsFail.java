package com.ramusthastudio.news.domain;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class NewsFail {
  private Long id;
  private String newsId;
  private String link;
  private Date pubDate;
  private String reason;
  private String reasonNote;
  private Date createdTime;
  private boolean fixed;
  private String fixedNote;
}
