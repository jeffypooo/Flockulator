package com.masterjefferson.flockulator.frameworks;

import android.content.Context;

import com.masterjefferson.flockulator.R;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator;
import com.masterjefferson.flockulator.frameworks.auth.T4JTwitterAuthenticator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
@Module
public class T4JModule {

  @Provides
  @Singleton
  TwitterAuthenticator provideTwitterAuthenticator(Context context) {
    String consumerKey    = context.getString(R.string.twitter_oauth_consumer_key);
    String consumerSecret = context.getString(R.string.twitter_oauth_consumer_secret);
    return new T4JTwitterAuthenticator(consumerKey, consumerSecret);
  }


}
