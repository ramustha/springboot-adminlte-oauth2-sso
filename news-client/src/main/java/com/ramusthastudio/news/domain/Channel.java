package com.ramusthastudio.news.domain;

import java.math.BigInteger;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Channel {
  private String channelTitle;
  private String channelName;
  private Date pubDate;
  private BigInteger count;
}