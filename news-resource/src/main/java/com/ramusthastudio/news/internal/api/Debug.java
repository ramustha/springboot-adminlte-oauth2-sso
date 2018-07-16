package com.ramusthastudio.news.internal.api;

import com.ramusthastudio.news.internal.domain.News;
import com.ramusthastudio.news.internal.domain.xml.Channel;
import com.ramusthastudio.news.internal.domain.xml.Image;
import com.ramusthastudio.news.internal.domain.xml.Item;
import com.ramusthastudio.news.internal.domain.xml.Rss;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Debug {
  private static final Logger LOG = LoggerFactory.getLogger(Debug.class);

  private Debug() {}

  public static void dumpChannel(Channel aChannel) {
    String title = aChannel.title;
    String description = aChannel.description;
    String pubDate = aChannel.pubDate;
    Image image = aChannel.image;
    List<Item> items = aChannel.items;

    StringBuilder b = new StringBuilder("=====\n")
        .append("Title:").append(title).append("\n")
        .append("Description:").append(description).append("\n")
        .append("PubDate:").append(pubDate).append("\n");

    if (image != null) {
      b.append("Image:").append("\n")
          .append("  Title:").append(image.title).append("\n")
          .append("  Link:").append(image.link).append("\n")
          .append("  Url:").append(image.url).append("\n");
    }

    if (items != null) {
      items.forEach(aItem -> {
        b.append("Items:").append("\n")
            .append("  Title:").append(aItem.title).append("\n")
            .append("  Link:").append(aItem.link).append("\n")
            .append("  Guid:").append(aItem.guid).append("\n")
            .append("  Category:").append(aItem.category).append("\n")
            .append("  PubDate:").append(aItem.pubDate).append("\n")
            .append("  Creator:").append(aItem.creator).append("\n")
            .append("  Description:").append(aItem.description).append("\n");
        if (aItem.enclosure != null) {
          b.append("  Enclosure:").append("\n")
              .append("    Url:").append(aItem.enclosure.url).append("\n")
              .append("    Length:").append(aItem.enclosure.length).append("\n")
              .append("    type:").append(aItem.enclosure.type).append("\n");
        }
        b.append("  Encoded:").append(aItem.encoded).append("\n");
        if (aItem.media != null) {
          b.append("  Media:").append("\n")
              .append("    Url:").append(aItem.media.url).append("\n");
        }
        if (aItem.url != null) {
          b.append("  Url:").append("\n")
              .append("    Url:").append(aItem.url.url).append("\n");
        }

      });
    }

    LOG.info(b.toString());
  }

  public static void dumpNews(List<News> aNewsList) {
    aNewsList.forEach(Debug::dumpNews);
  }

  public static void dumpNews(News aNews) {
    String id = aNews.getId();
    String chTitle = aNews.getChannelTitle();
    String chDesc = aNews.getChannelDescription();
    Instant chPubDate = aNews.getChannelPubDate();
    String chImageTitle = aNews.getChannelImageTitle();
    String chImageLink = aNews.getChannelImageLink();
    String chImageUrl = aNews.getChannelImageUrl();
    String title = aNews.getTitle();
    String link = aNews.getLink();
    String category = aNews.getCategory();
    Instant pubDate = aNews.getPubDate();
    String creator = aNews.getCreator();
    String description = aNews.getDescription();
    List<String> media = aNews.getMedia();
    String fullContent = aNews.getFullContent();
    Instant createdTime = aNews.getCreatedTime();

    StringBuilder b = new StringBuilder("=====\n")
        .append("Id:").append(id).append("\n")
        .append("Channel Title:").append(chTitle).append("\n")
        .append("Channel Desc:").append(chDesc).append("\n")
        .append("Channel PubDate:").append(chPubDate).append("\n")
        .append("Channel ImageTitle:").append(chImageTitle).append("\n")
        .append("Channel ImageLink:").append(chImageLink).append("\n")
        .append("Channel ImageUrl:").append(chImageUrl).append("\n")
        .append("Title:").append(title).append("\n")
        .append("Link:").append(link).append("\n")
        .append("Category:").append(category).append("\n")
        .append("PubDate:").append(pubDate).append("\n")
        .append("Creator:").append(creator).append("\n")
        .append("Description:").append(description).append("\n")
        .append("Media:").append("\n");

    media.forEach(aSrc -> b.append("  Src:").append(aSrc).append("\n"));
    b.append("FullContent:").append(fullContent).append("\n");
    b.append("CreatedTime:").append(createdTime).append("\n");

    LOG.info(b.toString());
  }

  public static Disposable setupSubscriber(Flowable<Rss> aObservable, ChannelListener aListener) {
    return Flowables.exec(aObservable, aListener)
        .doOnSubscribe(aDisposable -> LOG.info("Starting"))
        .doOnError(RetrofitService::handleOnErrorTarget)
        .doOnNext(Debug::dumpNews)
        .doOnComplete(() -> LOG.info("Completed"))
        .subscribe();
  }
}
