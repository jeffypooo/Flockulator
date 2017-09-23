package com.masterjefferson.flockulator.onboard.authorize;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.masterjefferson.flockulator.R;
import com.masterjefferson.flockulator.app.FlockulatorApp;
import com.masterjefferson.flockulator.domain.twitter.auth.AuthModule;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class AuthorizeActivity
    extends AppCompatActivity
    implements AuthorizeView {

  public static final String EXTRA_AUTH_URL = AuthorizeActivity.class.getCanonicalName() + ".EXTRA_AUTH_URL";

  @Inject TwitterAuthenticator   twitterAuthenticator;
  @Inject TwitterCredentialStore credentialStore;

  @BindView(R.id.Authorize_WebView) WebView webView;

  private AuthorizePresenter presenter;
  private String             callbackUrlOverride;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    configure();
    presenter.onCreate(this);
    handleIntent();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override
  public void openUrl(String url) {
    webView.loadUrl(url);
  }

  @Override
  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrlOverride = callbackUrl;
  }

  @Override
  public void close() {
    finish();
  }

  private void configure() {
    configureViews();
    configureDependencies();
  }

  private void configureViews() {
    setContentView(R.layout.activity_authorize);
    ButterKnife.bind(this);
    webView.setWebViewClient(new AuthCallbackClient());
  }

  private void configureDependencies() {
    ((FlockulatorApp) getApplication()).getAppComponent().inject(this);
    presenter = DaggerAuthorizeComponent.builder()
        .authModule(new AuthModule(twitterAuthenticator, credentialStore))
        .build()
        .presenter();
  }

  private void handleIntent() {
    Intent intent  = getIntent();
    String authUrl = intent.getStringExtra(EXTRA_AUTH_URL);
    presenter.onAuthUrlLoaded(authUrl);
  }

  private class AuthCallbackClient
      extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
      String requestUrlStr = request.getUrl().toString();
      if (requestUrlStr.startsWith(callbackUrlOverride)) {
        Timber.i("callback url override detected: %s", requestUrlStr);
        presenter.onCallbackUrlDetect(requestUrlStr);
        return true;
      }
      return super.shouldOverrideUrlLoading(view, request);
    }

  }
}
