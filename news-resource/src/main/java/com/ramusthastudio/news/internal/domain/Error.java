/*
 * Copyright (c) 2014-2017 Archeta All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */

package com.ramusthastudio.news.internal.domain;

import com.google.gson.annotations.SerializedName;

public final class Error {
  @SerializedName("status")
  private final String fStatus;
  @SerializedName("errorType")
  private final String fErrorType;
  @SerializedName("error")
  private final String fError;

  public Error(String aStatus, String aErrorType, String aError) {
    fStatus = aStatus;
    fErrorType = aErrorType;
    fError = aError;
  }

  @Override
  public String toString() {
    return "Error{" +
        "Status='" + fStatus + '\'' +
        ", ErrorType='" + fErrorType + '\'' +
        ", Error='" + fError + '\'' +
        '}';
  }

  public static Error defaultNetworkError() {
    return new Error("error", "bad_network", "could no get any response");
  }

  public static Error defaultUnexpectedError() {
    return new Error("error", "unexpected", "could no get any response");
  }
}
