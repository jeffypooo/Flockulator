package com.masterjefferson.flockulator.frameworks.auth;

import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator;
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public class T4JTwitterAuthenticator
    implements TwitterAuthenticator {
  private static final String CALLBACK_URL = "http://flock-me";
  private final TwitterFactory apiFactory;

  @Override
  public String getCallbackUrl() {
    return CALLBACK_URL;
  }

  private Twitter twitter;

  public T4JTwitterAuthenticator(String consumerKey, String consumerSecret) {
    Configuration conf = new ConfigurationBuilder()
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .build();
    this.apiFactory = new TwitterFactory(conf);
  }

  @Override
  public Observable<String> fetchAuthorizationURL() {
    Observable<String> urlFetcher = Observable.create(
        new ObservableOnSubscribe<String>() {
          @Override
          public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
            String authUrl = getApiInstance().getOAuthRequestToken(CALLBACK_URL).getAuthorizationURL();
            e.onNext(authUrl);
            e.onComplete();
          }
        });
    return urlFetcher.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }

  @Override
  public Observable<TwitterCredentials> authenticate(String token, final String verifier) {
    Observable<TwitterCredentials> authObs = Observable.create(new ObservableOnSubscribe<TwitterCredentials>() {
      @Override
      public void subscribe(@NonNull ObservableEmitter<TwitterCredentials> e) throws Exception {
        AccessToken accessToken = getApiInstance().getOAuthAccessToken(verifier);
        TwitterCredentials credentials = new TwitterCredentials(
            accessToken.getUserId(),
            accessToken.getToken(),
            accessToken.getTokenSecret()
        );
        e.onNext(credentials);
        e.onComplete();
      }
    });
    return authObs.subscribeOn(Schedulers.io());
  }

  private Twitter getApiInstance() {
    if (twitter == null) {
      twitter = apiFactory.getInstance();
    }
    return twitter;
  }
}
