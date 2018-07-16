package com.ramusthastudio.news.internal.api;

import com.ramusthastudio.news.internal.domain.News;
import com.ramusthastudio.news.internal.domain.xml.Item;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static com.ramusthastudio.news.internal.api.ExtractorHelper.findFullContent;
import static com.ramusthastudio.news.internal.api.ExtractorHelper.isContains;
import static com.ramusthastudio.news.internal.api.IndonesianChannel.DETIK;
import static com.ramusthastudio.news.internal.api.IndonesianChannel.toLink;
import static com.ramusthastudio.news.internal.api.IndonesianChannel.toName;

public final class IndonesiaExtractor {
  private static final String REMOVED_TAG = "script, style, aside ,table, .hidden";

  private IndonesiaExtractor() {}

  public static void imageAndDescExtractor(News aNews, List<String> aMedia, Item aItem) {
    if (aItem.description != null) {
      if (isContains(aItem.link, "antaranews")
          || (isContains(aItem.link, "detik.com"))
          || (isContains(aItem.link, "hidayatullah.com"))
          || (isContains(aItem.link, "jpnn.com"))
          || (isContains(aItem.link, "kapanlagi.com"))
          || (isContains(aItem.link, "kontan.co.id"))
          || (isContains(aItem.link, "www.merdeka.com"))
          || (isContains(aItem.link, "pikiran-rakyat.com"))
          || (isContains(aItem.link, "sidomi.com"))
          || isContains(aItem.link, "tribunnews.com")
          || isContains(aItem.link, "viva.co.id")) {

        Document doc = Jsoup.parse(aItem.description);
        Elements imgSrc = doc.select("img");

        aMedia.add(imgSrc.attr("src"));
        aNews.setDescription(doc.text());
      } else if (isContains(aItem.link, "ini.la")
          || (isContains(aItem.link, "metrotvnews.com"))
          || (isContains(aItem.link, "okezone.com"))
          || (isContains(aItem.link, "poskotanews.com"))
          || (isContains(aItem.link, "chip.co.id"))
          || (isContains(aItem.link, "republika.co.id"))
          || isContains(aItem.link, "ihram.co.id")
          || isContains(aItem.link, "sindonews.com")
          || isContains(aItem.link, "suaramerdeka.com")
          || isContains(aItem.link, "tempo.co")
          || isContains(aItem.link, "http://waspada.co.id")) {
        aNews.setDescription(aItem.description);
      } else if (isContains(aItem.link, "liputan6.com")
          || isContains(aItem.link, "indopos.co.id")
          || isContains(aItem.link, "voaindonesia.com")) {
        aNews.setDescription(aItem.title);
        aNews.setFullContent(aItem.description);
      }
    }

    if (aItem.enclosure != null) {

      if (isContains(aItem.link, "bisnis.com")
          || isContains(aItem.link, "tabloidbintang.com")) {
        aMedia.add(aItem.enclosure.url);
        aNews.setDescription(aItem.description);
      } else if (isContains(aItem.link, "jpnn.com")
          || (isContains(aItem.link, "kapanlagi.com"))
          || isContains(aItem.link, "voaindonesia.com")) {
        aMedia.add(aItem.enclosure.url);
      }
    }

    if (aItem.encoded != null) {
      if (isContains(aItem.link, "hidayatullah.com")
          || (isContains(aItem.link, "ini.la"))
          || (isContains(aItem.link, "metrotvnews.com"))
          || isContains(aItem.link, "suaramerdeka.com")) {
        aNews.setFullContent(aItem.encoded);
      }
    }

    if (aItem.url != null) {
      if (isContains(aItem.link, "okezone.com")) {
        aMedia.add(aItem.url.url);
      }
    }

    if (aItem.media != null) {
      if (isContains(aItem.link, "republika.co.id")
          || isContains(aItem.link, "ihram.co.id")
          || isContains(aItem.link, "sindonews.com")) {
        aMedia.add(aItem.media.url);
      }
    }
  }

  public static News contentExtractor(News aNews, String aRaw, ChannelListener aListener) {
    Document doc = Jsoup.parse(aRaw);
    doc.select(REMOVED_TAG).remove();

    if (isContains(aNews.getLink(), "detik.com")) {

      final String contentCss = "div[class=detail_text]/div[class=text_detail]/div[id=detikdetailtext]";
      final String cleanQuery = "div.detail_tag, div.clearfix, div.boxlr, div.box_hl, div.share-box";
      findFullContent(doc, contentCss, cleanQuery, aNews, aListener);

    }
    return aNews;
  }

  public static String imageReplacer(String aSrc) {
    if (isContains(aSrc, "detik.com")) {
      aSrc += "?w=780&q=90";
    }
    return aSrc;
  }

  public static String generateChannelName(String aLink) {
    if (aLink.contains(toLink(DETIK))) {
      return toName(DETIK);
    } else {
      return "Unknown";
    }
  }
}