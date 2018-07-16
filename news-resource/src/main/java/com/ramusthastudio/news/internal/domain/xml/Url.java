package com.ramusthastudio.news.internal.domain.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "url", strict = false)
public class Url {
  @Element(name = "url", required = false, data = true)
  public String url;

  @Override
  public String toString() {
    return "Url{" +
        "url='" + url + '\'' +
        '}';
  }
}
