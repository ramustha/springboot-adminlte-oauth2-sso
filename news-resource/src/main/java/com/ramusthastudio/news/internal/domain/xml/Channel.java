package com.ramusthastudio.news.internal.domain.xml;

import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "channel", strict = false)
public class Channel {
  @Element(name = "title", required = false, data = true)
  public String title;
  @Element(name = "description", required = false, data = true)
  public String description;
  @Element(name = "pubDate", required = false, data = true)
  public String pubDate;
  @Element(name = "image", required = false, data = true)
  public Image image;
  @ElementList(name = "item", inline = true, required = false, data = true)
  public List<Item> items;

  @Override
  public String toString() {
    return "Channel{" +
        "title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", pubDate='" + pubDate + '\'' +
        ", image=" + image +
        ", items=" + items +
        '}';
  }
}
