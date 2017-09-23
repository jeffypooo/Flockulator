package com.masterjefferson.flockulator.domain.twitter.auth.data;

import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;

import io.reactivex.Observable;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public interface TwitterAuthenticator {

  String getCallbackUrl();
  Observable<String> fetchAuthorizationURL();
  Observable<TwitterCredentials> authenticate(String requestToken, String verificationToken);

}
