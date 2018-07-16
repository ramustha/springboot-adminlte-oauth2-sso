package com.ramusthastudio.news.internal.domain.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "media", strict = false)
public class Media {
  @Attribute(name = "url", required = false)
  public String url;

  @Override
  public String toString() {
    return "Media{" +
        "url='" + url + '\'' +
        '}';
  }
}
