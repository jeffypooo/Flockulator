package com.masterjefferson.flockulator.domain.twitter.auth;

import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore;
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.DoAuthenticate;
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.FetchAuthUrl;
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.GetCallbackUrl;
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.GetCredentials;
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.StoreCredentials;

import dagger.Module;
import dagger.Provides;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
@Module
public class AuthModule {

  private final TwitterAuthenticator   authenticator;
  private final TwitterCredentialStore credentialStore;

  public AuthModule(TwitterAuthenticator authenticator, TwitterCredentialStore credentialStore) {
    this.authenticator = authenticator;
    this.credentialStore = credentialStore;
  }

  @Provides
  FetchAuthUrl provideFetchAuthUrl() {
    return new FetchAuthUrl(authenticator);
  }

  @Provides
  GetCallbackUrl provideGetCallbackUrl() {
    return new GetCallbackUrl(authenticator);
  }

  @Provides
  DoAuthenticate provideDoAuthenticate() {return new DoAuthenticate(authenticator);}

  @Provides
  StoreCredentials provideStoreCredentials() { return new StoreCredentials(credentialStore); }

  @Provides
  GetCredentials provideGetCredentials() { return new GetCredentials(credentialStore); }

}
