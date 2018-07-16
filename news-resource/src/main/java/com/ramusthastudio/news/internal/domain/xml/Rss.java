package com.ramusthastudio.news.internal.domain.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class Rss {
  @Element(name = "channel", required = false, data = true)
  public Channel channel;
}
