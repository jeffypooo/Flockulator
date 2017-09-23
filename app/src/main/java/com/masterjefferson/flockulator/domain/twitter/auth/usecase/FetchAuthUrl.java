package com.masterjefferson.flockulator.domain.twitter.auth.usecase;

import com.masterjefferson.flockulator.domain.RxUseCase;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator;

import io.reactivex.Observable;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public class FetchAuthUrl
    extends RxUseCase<Void, String> {

  private final TwitterAuthenticator authenticator;

  public FetchAuthUrl(TwitterAuthenticator authenticator) {this.authenticator = authenticator;}

  @Override
  public Observable<String> rx(Void args) {
    return authenticator.fetchAuthorizationURL();
  }
}
