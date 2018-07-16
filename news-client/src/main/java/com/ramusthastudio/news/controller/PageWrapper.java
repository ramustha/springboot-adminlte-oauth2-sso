package com.ramusthastudio.news.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

public final class PageWrapper<T> {
  public static final int MAX_PAGE_ITEM_DISPLAY = 5;
  private final Page<T> fPage;
  private final List<PageItem> fItems = new ArrayList<>();
  private final int fCurrentNumber;
  private String fUrl;

  public PageWrapper(Page<T> aPage, String aUrl) {
    fPage = aPage;
    fUrl = aUrl;
    fCurrentNumber = aPage.getNumber() + 1; //start from 1 to match page.page

    int start;
    int size;
    if (aPage.getTotalPages() <= MAX_PAGE_ITEM_DISPLAY) {
      start = 1;
      size = aPage.getTotalPages();
    } else {
      if (fCurrentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY / 2) {
        start = 1;
        size = MAX_PAGE_ITEM_DISPLAY;
      } else if (fCurrentNumber >= aPage.getTotalPages() - MAX_PAGE_ITEM_DISPLAY / 2) {
        start = aPage.getTotalPages() - MAX_PAGE_ITEM_DISPLAY + 1;
        size = MAX_PAGE_ITEM_DISPLAY;
      } else {
        start = fCurrentNumber - MAX_PAGE_ITEM_DISPLAY / 2;
        size = MAX_PAGE_ITEM_DISPLAY;
      }
    }

    for (int i = 0; i < size; i++) {
      fItems.add(new PageItem(start + i, (start + i) == fCurrentNumber));
    }
  }
  public void setUrl(String url) {
    fUrl = url;
  }

  public String getUrl() {
    return fUrl;
  }

  public List<PageItem> getItems() {
    return fItems;
  }

  public int getNumber() {
    return fCurrentNumber;
  }

  public List<T> getContent() {
    return fPage.getContent();
  }

  public int getSize() {
    return fPage.getSize();
  }

  public int getTotalPages() {
    return fPage.getTotalPages();
  }

  public long getTotalElements() {return fPage.getTotalElements();}

  public boolean isFirstPage() {
    return fPage.isFirst();
  }

  public boolean isLastPage() {
    return fPage.isLast();
  }

  public boolean isHasPreviousPage() {
    return fPage.hasPrevious();
  }

  public boolean isHasNextPage() {
    return fPage.hasNext();
  }

  @Getter
  @AllArgsConstructor
  @Accessors(chain = true)
  public final class PageItem {
    private final int number;
    private final boolean current;
  }
}
