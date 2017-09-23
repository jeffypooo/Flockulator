package com.masterjefferson.flockulator.domain.twitter.auth.usecase;

import com.masterjefferson.flockulator.domain.UseCase;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public class GetCallbackUrl
    extends UseCase<Void, String> {

  private final TwitterAuthenticator twitterAuthenticator;

  public GetCallbackUrl(TwitterAuthenticator twitterAuthenticator) {this.twitterAuthenticator = twitterAuthenticator;}

  @Override
  public String run(Void args) {
    return twitterAuthenticator.getCallbackUrl();
  }
}
