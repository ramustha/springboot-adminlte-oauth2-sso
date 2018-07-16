package com.ramusthastudio.news.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramusthastudio.news.domain.Channel;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelPage extends PageImpl<Channel> {
  @JsonCreator
  // Note: I don't need a sort, so I'm not including one here.
  // It shouldn't be too hard to add it in tho.
  public ChannelPage(
      @JsonProperty("content") List<Channel> content,
      @JsonProperty("number") int number,
      @JsonProperty("size") int size,
      @JsonProperty("totalElements") Long totalElements) {
    super(content, new PageRequest(number, size), totalElements);
  }
}
