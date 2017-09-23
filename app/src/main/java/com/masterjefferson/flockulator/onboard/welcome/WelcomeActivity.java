package com.masterjefferson.flockulator.onboard.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.masterjefferson.flockulator.R;
import com.masterjefferson.flockulator.app.FlockulatorApp;
import com.masterjefferson.flockulator.domain.twitter.auth.AuthModule;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore;
import com.masterjefferson.flockulator.main.RootActivity;
import com.masterjefferson.flockulator.onboard.authorize.AuthorizeActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WelcomeActivity
    extends AppCompatActivity
    implements WelcomeView {

  private static final String TAG = WelcomeActivity.class.getSimpleName();

  @BindView(R.id.Welcome_AuthButton)       Button      authorizeButton;
  @BindView(R.id.Welcome_LoadingIndicator) ProgressBar loadingIndicator;

  @Inject TwitterAuthenticator   twitterAuthenticator;
  @Inject TwitterCredentialStore credentialStore;

  private Unbinder         unbinder;
  private WelcomePresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    configure();
    presenter.onCreate(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.onResume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
    unbinder.unbind();
  }

  @OnClick(R.id.Welcome_AuthButton)
  void onAuthClick() {
    presenter.onAuthorizeClick();
  }

  @Override
  public void openAuthorizationPage(String url) {
    Intent intent = new Intent(this, AuthorizeActivity.class);
    intent.putExtra(AuthorizeActivity.EXTRA_AUTH_URL, url);
    startActivity(intent);
  }

  @Override
  public void setLoading(boolean loading) {
    authorizeButton.setEnabled(!loading);
    float opacity = loading ? 0.5f : 1f;
    authorizeButton.animate()
        .alpha(opacity)
        .setDuration(250)
        .start();
    loadingIndicator.setVisibility(loading ? View.VISIBLE : View.GONE);
  }

  @Override
  public void proceedToApplication() {
    Intent intent = new Intent(this, RootActivity.class);
    startActivity(intent);
    finish();
  }

  private void configure() {
    configureViews();
    configureDependencies();
  }

  private void configureViews() {
    setContentView(R.layout.activity_welcome);
    unbinder = ButterKnife.bind(this);
  }

  private void configureDependencies() {
    ((FlockulatorApp) getApplication()).getAppComponent().inject(this);
    this.presenter = DaggerWelcomeComponent.builder()
        .authModule(new AuthModule(twitterAuthenticator, credentialStore))
        .build()
        .presenter();
  }

}
