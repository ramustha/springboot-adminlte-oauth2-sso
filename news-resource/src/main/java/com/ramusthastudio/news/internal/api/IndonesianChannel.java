package com.ramusthastudio.news.internal.api;

public enum IndonesianChannel {
  DETIK("Detik", "detik.com");

  private final String fName;
  private final String fLink;

  IndonesianChannel(String aName, String aLink) {
    fName = aName;
    fLink = aLink;
  }

  public String getName() { return fName; }

  public String getLink() { return fLink; }

  public static String toName(IndonesianChannel aIndonesianChannel) {
    return aIndonesianChannel.getName();
  }

  public static String toLink(IndonesianChannel aIndonesianChannel) {
    return aIndonesianChannel.getLink();
  }
}
