package com.ramusthastudio.news.internal.domain.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "image", strict = false)
public class Image {
  @Element(name = "title", required = false, data = true)
  public String title;
  @Element(name = "link", required = false, data = true)
  public String link;
  @Element(name = "url", required = false, data = true)
  public String url;

  @Override
  public String toString() {
    return "Image{" +
        "title='" + title + '\'' +
        ", link='" + link + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
