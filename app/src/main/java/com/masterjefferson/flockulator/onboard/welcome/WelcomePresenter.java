package com.masterjefferson.flockulator.onboard.welcome;

import com.masterjefferson.flockulator.app.FlockulatorActivityPresenter;
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.FetchAuthUrl;
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.GetCredentials;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public class WelcomePresenter
    extends FlockulatorActivityPresenter<WelcomeView> {

  private final FetchAuthUrl   fetchAuthUrl;
  private final GetCredentials getCredentials;
  private       Disposable     fetchUrlDisposable;

  @Inject
  public WelcomePresenter(FetchAuthUrl fetchAuthUrl, GetCredentials getCredentials) {
    this.fetchAuthUrl = fetchAuthUrl;
    this.getCredentials = getCredentials;
  }

  @Override
  public void onResume() {
    super.onResume();
    checkForExistingCredentials();
  }

  void onAuthorizeClick() {
    viewSetLoading(true);
    Timber.d("fetching auth url...");
    this.fetchUrlDisposable = fetchAuthUrl.rx()
        .subscribe(
            new Consumer<String>() {
              @Override
              public void accept(String url) throws Exception {
                viewSetLoading(false);
                viewOpenAuthorizationPage(url);
              }
            }
        );
  }

  private void checkForExistingCredentials() {
    TwitterCredentials credentials = getCredentials.run();
    if (credentials == null) {
      Timber.d("no existing credentials");
      return;
    }
    Timber.d("have existing credentials: %s", credentials);
    viewProceedToApplication();
  }

  private void viewSetLoading(boolean loading) {
    if (view == null) {
      warnViewNull("viewSetLoading");
      return;
    }
    logViewAction("set loading %b", loading);
    view.setLoading(loading);
  }

  private void viewOpenAuthorizationPage(String url) {
    if (view == null) {
      warnViewNull("viewOpenAuthorizationPage");
      return;
    }
    logViewAction("opening auth url: %s", url);
    view.openAuthorizationPage(url);
  }

  private void viewProceedToApplication() {
    if (view == null) {
      warnViewNull("viewProceedToApplication");
      return;
    }
    logViewAction("proceeding to application");
    view.proceedToApplication();
  }
}
