package com.masterjefferson.flockulator.frameworks.t4j;

import twitter4j.StatusListener;
import twitter4j.TwitterStream;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/24/17.
 */

public class T4JCompat {

  public static void addStatusListener(TwitterStream stream, StatusListener listener) {
    stream.addListener(listener);
  }

  public static void removeStatusListener(TwitterStream stream, StatusListener listener) {
    stream.removeListener(listener);
  }

}
