package com.ramusthastudio.news.internal.domain.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "enclosure", strict = false)
public class Enclosure {
  @Attribute(name = "url")
  public String url;
  @Attribute(name = "length", required = false)
  public String length;
  @Attribute(name = "type", required = false)
  public String type;

  @Override
  public String toString() {
    return "Enclosure{" +
        "url='" + url + '\'' +
        ", length='" + length + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
