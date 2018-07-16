package com.ramusthastudio.news.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramusthastudio.news.domain.Category;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryPage extends PageImpl<Category> {
  @JsonCreator
  // Note: I don't need a sort, so I'm not including one here.
  // It shouldn't be too hard to add it in tho.
  public CategoryPage(
      @JsonProperty("content") List<Category> content,
      @JsonProperty("number") int number,
      @JsonProperty("size") int size,
      @JsonProperty("totalElements") Long totalElements) {
    super(content, new PageRequest(number, size), totalElements);
  }
}
