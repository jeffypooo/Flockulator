package com.masterjefferson.flockulator.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.masterjefferson.flockulator.R;
import com.masterjefferson.flockulator.app.FlockulatorApp;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore;
import com.masterjefferson.flockulator.domain.twitter.model.BasicTweet;
import com.masterjefferson.flockulator.domain.twitter.stream.data.TwitterStreamingApi;
import com.masterjefferson.flockulator.frameworks.stream.T4JTwitterStreamingApi;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class RootActivity
    extends AppCompatActivity {

  @Inject TwitterCredentialStore credentialStore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_root);
    configure();
    TwitterStreamingApi streamingApi = new T4JTwitterStreamingApi(credentialStore);
    streamingApi.sample().subscribe(
        new Consumer<BasicTweet>() {
          @Override
          public void accept(BasicTweet basicTweet) throws Exception {
            Timber.i("tweet - %s", basicTweet);
          }
        },
        new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            Timber.e(throwable, "error");
            finish();
          }
        }
    );
  }

  private void configure() {
    ((FlockulatorApp) getApplication()).getAppComponent().inject(this);
  }
}
