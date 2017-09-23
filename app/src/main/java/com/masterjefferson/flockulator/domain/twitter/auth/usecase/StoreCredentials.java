package com.masterjefferson.flockulator.domain.twitter.auth.usecase;

import com.masterjefferson.flockulator.domain.UseCase;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore;
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public class StoreCredentials
    extends UseCase<TwitterCredentials, Void> {

  private final TwitterCredentialStore store;

  public StoreCredentials(TwitterCredentialStore store) {this.store = store;}

  @Override
  public Void run(TwitterCredentials args) {
    store.storeCredentials(args);
    return null;
  }
}
