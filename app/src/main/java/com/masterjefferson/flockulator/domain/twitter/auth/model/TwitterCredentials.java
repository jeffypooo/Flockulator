package com.masterjefferson.flockulator.domain.twitter.auth.model;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public class TwitterCredentials {
  public final long    userId;
  public final String accessToken;
  public final String tokenSecret;

  public TwitterCredentials(long userId, String accessToken, String tokenSecret) {
    this.userId = userId;
    this.accessToken = accessToken;
    this.tokenSecret = tokenSecret;
  }

  @Override
  public String toString() {
    return "TwitterCredentials{" +
        "userId=" + userId +
        ", accessToken='" + accessToken + '\'' +
        ", tokenSecret='" + tokenSecret + '\'' +
        '}';
  }
}
