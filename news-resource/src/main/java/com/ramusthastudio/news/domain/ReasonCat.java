package com.ramusthastudio.news.domain;

public enum ReasonCat {
  FAILED_PARSING,
  FAILED_MERGE,
  FAILED_LOAD_CONTENT,
  FAILED_EXTRACT_CONTENT,
  CONTENT_NOT_FOUND,
  CONTENT_TO_SHORT
}
