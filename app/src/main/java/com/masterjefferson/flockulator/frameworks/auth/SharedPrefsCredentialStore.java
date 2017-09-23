package com.masterjefferson.flockulator.frameworks.auth;

import android.content.Context;
import android.content.SharedPreferences;

import com.masterjefferson.flockulator.R;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore;
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public class SharedPrefsCredentialStore
    implements TwitterCredentialStore {

  private static final String PREFS_NAME = SharedPrefsCredentialStore.class.getCanonicalName();
  private static final String KEY_USER_ID = PREFS_NAME + ".USER_ID";
  private static final String KEY_ACCESS_TOKEN = PREFS_NAME + ".ACCESS_TOKEN";
  private static final String KEY_ACCESS_SECRET = PREFS_NAME + ".ACCESS_SECRET";


  private final Context context;

  public SharedPrefsCredentialStore(Context context) {this.context = context;}

  @Override
  public String getConsumerKey() {
    return context.getString(R.string.twitter_oauth_consumer_key);
  }

  @Override
  public String getConsumerSecret() {
    return context.getString(R.string.twitter_oauth_consumer_secret);
  }

  @Override
  public void storeCredentials(TwitterCredentials credentials) {
    SharedPreferences.Editor editor = getPrefs().edit();
    editor.putLong(KEY_USER_ID, credentials.userId);
    editor.putString(KEY_ACCESS_TOKEN, credentials.accessToken);
    editor.putString(KEY_ACCESS_SECRET, credentials.tokenSecret);
    editor.commit();
  }

  @Override
  public TwitterCredentials getCredentials() {
    SharedPreferences prefs = getPrefs();
    long userId = prefs.getLong(KEY_USER_ID, -1);
    if (userId < 0) {
      return null;
    }
    String token = prefs.getString(KEY_ACCESS_TOKEN, "");
    String secret = prefs.getString(KEY_ACCESS_SECRET, "");
    return new TwitterCredentials(userId, token, secret);
  }

  private SharedPreferences getPrefs() {
    return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
  }
}
