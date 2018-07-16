package com.ramusthastudio.news.internal.api;

import com.ramusthastudio.news.domain.ReasonCat;
import com.ramusthastudio.news.internal.domain.News;
import com.ramusthastudio.news.internal.domain.xml.Channel;
import com.ramusthastudio.news.internal.domain.xml.Image;
import com.ramusthastudio.news.internal.domain.xml.Item;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ramusthastudio.news.internal.api.IndonesiaExtractor.generateChannelName;

public final class ExtractorHelper {
  private static final Logger LOG = LoggerFactory.getLogger(ExtractorHelper.class);

  private ExtractorHelper() { }

  public static List<News> intoNews(Channel aChannel) {
    List<News> newsList = new ArrayList<>();

    String title = aChannel.title;
    String description = aChannel.description;
    String pubDate = aChannel.pubDate;
    Image image = aChannel.image;
    List<Item> items = aChannel.items;

    if (items != null) {
      items.forEach(aItem -> {
        List<String> media = new ArrayList<>();
        News news = new News();

        news.setChannelTitle(title)
            .setChannelDescription(description)
            .setChannelPubDate(convertDate(pubDate));

        if (image != null) {
          String imageTitle = image.title;
          String imageLink = image.link;
          String imageUrl = image.url;

          news.setChannelImageTitle(imageTitle)
              .setChannelImageUrl(imageLink)
              .setChannelImageLink(imageUrl);
        }

        String itemTitle = aItem.title;
        String itemLink = aItem.link;
        String itemGuid = aItem.guid;
        String itemCategory = aItem.category == null ? "Unknown" : aItem.category;
        String itemPubDate = aItem.pubDate;
        String itemCreator = aItem.creator;

        news.setId(itemGuid)
            .setTitle(itemTitle)
            .setLink(itemLink)
            .setChannelName(generateChannelName(itemLink))
            .setCategory(itemCategory)
            .setPubDate(convertDate(itemPubDate))
            .setCreator(itemCreator);

        IndonesiaExtractor.imageAndDescExtractor(news, media, aItem);
        news.setMedia(media);
        news.setCreatedTime(Instant.now());
        newsList.add(news);
      });
    }

    return newsList;
  }

  public static Instant convertDate(String aDateStr) {
    List<SimpleDateFormat> knownPatterns = new ArrayList<>();
    knownPatterns.add(new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.US));
    knownPatterns.add(new SimpleDateFormat("EEE, dd MMMM yyyy kk:mm:ss", Locale.US));
    knownPatterns.add(new SimpleDateFormat("EEEE, dd MMMM yyyy kk:mm:ss", Locale.US));
    knownPatterns.add(new SimpleDateFormat("EEEE, d MMMM yyyy kk:mm:ss", new Locale("ID")));
    for (SimpleDateFormat knownPattern : knownPatterns) {
      try {
        return knownPattern.parse(aDateStr.trim()).toInstant();
      } catch (Exception ignored) {
      }
    }
    return Instant.now();
  }

  public static boolean isContains(String aS1, String aS2) {
    return aS1 != null && aS1.contains(aS2);
  }

  public static void findFullContent(
      Document doc,
      String aQueries,
      String aCleanQuery,
      News aNews,
      ChannelListener aListener) {
    findFullContent(doc, aQueries, aCleanQuery, "", aNews, aListener);
  }

  public static void findFullContent(
      Document aDoc,
      String aQueries,
      String aCleanQuery,
      String aCleanImgContains,
      News aNews,
      ChannelListener aListener) {

    removeEmptyBlock(aDoc);
    removeComments(aDoc);

    String[] splitedQueries = aQueries.split("[/]");
    for (String query : splitedQueries) {
      Elements fullContent = aDoc.select(query);
      if (!fullContent.hasText()) {
        aListener.onContentNotFound(ReasonCat.CONTENT_NOT_FOUND, query, aNews);
        LOG.error("Content not found with query: {}, link: {}", query, aNews.getLink());
      } else if (fullContent.html().length() < 200) {
        aListener.onContentTooShort(ReasonCat.CONTENT_TO_SHORT, query, aNews);
        LOG.error("Content too short with query: {}, link: {}", query, aNews.getLink());
      } else {
        findImages(fullContent, aCleanImgContains, aNews);
        fullContent.select(aCleanQuery).remove();

        if (aNews.getFullContent() == null) {
          aNews.setFullContent(fullContent.html());
        }
      }
    }
  }

  public static void findImages(Elements aElements, News aNews) {
    findImages(aElements, "", aNews);
  }

  public static void findImages(Elements aElements, String aCleanImgContains, News aNews) {
    Elements imgs = aElements.select("img");

    List<String> media = aNews.getMedia();
    for (Element img : imgs) {
      String src = img.attr("src");
      if (!src.contains(".gif")) {
        if (!aCleanImgContains.isEmpty()) {
          for (String s : aCleanImgContains.split("/")) {
            if (!src.contains(s)) {
              media.add(src);
            }
          }
        } else {
          media.add(src);
        }
      }
    }
  }

  public static void removeEmptyBlock(Document aDoc) {
    for (Element element : aDoc.select("*")) {
      if (!element.hasText() && element.isBlock()) {
        element.remove();
      }
    }
  }

  public static void removeComments(Node node) {
    for (int i = 0; i < node.childNodeSize(); ) {
      Node child = node.childNode(i);
      if (child.nodeName().equals("#comment")) {
        child.remove();
      } else {
        i++;
        removeComments(child);
      }
    }
  }
}
