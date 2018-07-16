package com.ramusthastudio.news.internal.domain.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "item", strict = false)
public class Item {
  @Path("title")
  @Text(data = true)
  public String title;
  @Path("link")
  @Text(data = true)
  public String link;
  @Path("guid")
  @Text(data = true)
  public String guid;
  @Path("getCategories")
  @Text(required = false, data = true)
  public String category;
  @Path("pubDate")
  @Text(required = false, data = true)
  public String pubDate;
  @Element(name = "creator", required = false, data = true)
  @Namespace(prefix = "dc")
  public String creator;
  @Path("description")
  @Text(required = false, data = true)
  public String description;
  @Element(name = "enclosure", required = false, data = true)
  public Enclosure enclosure;
  @Path("encoded")
  @Text(required = false, data = true)
  @Namespace(prefix = "content")
  public String encoded;
  @Element(name = "content", required = false, data = true)
  @Namespace(prefix = "media")
  public Media media;
  @Element(name = "image", required = false, data = true)
  public Url url;

  @Override
  public String toString() {
    return "Item{" +
        "title='" + title + '\'' +
        ", link='" + link + '\'' +
        ", guid='" + guid + '\'' +
        ", getCategories='" + category + '\'' +
        ", pubDate='" + pubDate + '\'' +
        ", creator='" + creator + '\'' +
        ", description='" + description + '\'' +
        ", enclosure=" + enclosure +
        ", encoded='" + encoded + '\'' +
        ", media=" + media +
        ", url=" + url +
        '}';
  }
}
