package com.masterjefferson.flockulator.domain.twitter.auth.usecase;

import com.masterjefferson.flockulator.domain.UseCase;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore;
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public class GetCredentials
    extends UseCase<Void, TwitterCredentials> {

  private final TwitterCredentialStore store;

  public GetCredentials(TwitterCredentialStore store) {this.store = store;}

  @Override
  public TwitterCredentials run(Void args) {
    return store.getCredentials();
  }
}
