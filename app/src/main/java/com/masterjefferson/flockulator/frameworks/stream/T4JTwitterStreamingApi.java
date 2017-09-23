package com.masterjefferson.flockulator.frameworks.stream;

import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore;
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;
import com.masterjefferson.flockulator.domain.twitter.model.BasicTweet;
import com.masterjefferson.flockulator.domain.twitter.stream.data.TwitterStreamingApi;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public class T4JTwitterStreamingApi
    implements TwitterStreamingApi {

  private final TwitterCredentialStore credentialStore;
  private       TwitterStream          twitterStream;

  public T4JTwitterStreamingApi(TwitterCredentialStore credentialStore) {
    this.credentialStore = credentialStore;
  }

  @Override
  public Observable<BasicTweet> sample() {
    return Observable.create(new ObservableOnSubscribe<BasicTweet>() {
      @Override
      public void subscribe(@NonNull final ObservableEmitter<BasicTweet> e) throws Exception {
        TwitterStream api = getApiInstance();
        StatusListener listener = new BasicStatusListener() {
          @Override
          public void onStatus(Status status) {
            BasicTweet basicTweet = new BasicTweet(
                status.getId(),
                status.getCreatedAt(),
                status.getText(),
                status.getUser().getName()
            );
            e.onNext(basicTweet);
          }

          @Override
          public void onException(Exception ex) {
            e.onError(ex);
          }
        };
        api.addListener(listener);
        e.setDisposable(new StatusListenerDisposable(api, listener));
        api.sample();
      }
    });
  }

  private TwitterStream getApiInstance() {
    if (twitterStream == null) {
      TwitterCredentials credentials = credentialStore.getCredentials();
      if (credentials == null) {
        throw new NullPointerException("no stored credentials");
      }
      Configuration conf = new ConfigurationBuilder()
          .setOAuthConsumerKey(credentialStore.getConsumerKey())
          .setOAuthConsumerSecret(credentialStore.getConsumerSecret())
          .setOAuthAccessToken(credentials.accessToken)
          .setOAuthAccessTokenSecret(credentials.tokenSecret)
          .build();
      twitterStream = new TwitterStreamFactory(conf).getInstance();
    }
    return twitterStream;
  }

  private static class StatusListenerDisposable
      implements Disposable {

    private final TwitterStream  api;
    private final StatusListener listener;
    private boolean disposed = false;

    private StatusListenerDisposable(TwitterStream api, StatusListener listener) {
      this.api = api;
      this.listener = listener;
    }

    @Override
    public void dispose() {
      api.removeListener(listener);
      disposed = true;
    }

    @Override
    public boolean isDisposed() {
      return disposed;
    }
  }

  private static abstract class BasicStatusListener implements StatusListener {
    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
      //no-op
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
      //no-op
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
      //no-op
    }

    @Override
    public void onStallWarning(StallWarning warning) {
      //no-op
    }
  }

}
