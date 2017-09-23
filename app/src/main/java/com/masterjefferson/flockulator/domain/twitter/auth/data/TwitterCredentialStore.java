package com.masterjefferson.flockulator.domain.twitter.auth.data;

import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public interface TwitterCredentialStore {
  String getConsumerKey();
  String getConsumerSecret();
  void storeCredentials(TwitterCredentials credentials);
  TwitterCredentials getCredentials();
}
