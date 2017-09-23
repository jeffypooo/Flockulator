package com.masterjefferson.flockulator.domain.twitter.model;

import java.util.Date;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public class BasicTweet {

  private final long   id;
  private final Date   timestamp;
  private final String text;
  private final String userName;

  public BasicTweet(long id, Date timestamp, String text, String userName) {
    this.id = id;
    this.timestamp = timestamp;
    this.text = text;
    this.userName = userName;
  }

  public long getId() {
    return id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getText() {
    return text;
  }

  public String getUserName() {
    return userName;
  }

  @Override
  public String toString() {
    return "BasicTweet{" +
        "id=" + id +
        ", timestamp=" + timestamp +
        ", text='" + text + '\'' +
        ", userName='" + userName + '\'' +
        '}';
  }
}
