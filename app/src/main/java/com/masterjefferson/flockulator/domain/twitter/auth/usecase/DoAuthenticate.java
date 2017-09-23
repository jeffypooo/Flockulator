package com.masterjefferson.flockulator.domain.twitter.auth.usecase;

import com.masterjefferson.flockulator.domain.RxUseCase;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator;
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;

import io.reactivex.Observable;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public class DoAuthenticate
    extends RxUseCase<DoAuthenticate.DoAuthenticateArgs, TwitterCredentials> {

  private final TwitterAuthenticator authenticator;

  public DoAuthenticate(TwitterAuthenticator authenticator) {this.authenticator = authenticator;}

  @Override
  public Observable<TwitterCredentials> rx(DoAuthenticateArgs args) {
    return authenticator.authenticate(args.requestToken, args.verifier);
  }

  public static class DoAuthenticateArgs {
    public final String requestToken, verifier;

    public DoAuthenticateArgs(String requestToken, String verifier) {
      this.requestToken = requestToken;
      this.verifier = verifier;
    }
  }

}
