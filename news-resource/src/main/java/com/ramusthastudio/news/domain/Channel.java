package com.ramusthastudio.news.domain;

import java.math.BigInteger;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Channel {
  private String channelTitle;
  private String channelName;
  private Date pubDate;
  private BigInteger count;
}