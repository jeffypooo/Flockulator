package com.masterjefferson.flockulator.onboard.authorize;

import com.masterjefferson.flockulator.app.FlockulatorActivityPresenter;
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.DoAuthenticate;
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.GetCallbackUrl;
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.StoreCredentials;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public class AuthorizePresenter
    extends FlockulatorActivityPresenter<AuthorizeView> {

  private final GetCallbackUrl   getCallbackUrl;
  private final DoAuthenticate   doAuthenticate;
  private final StoreCredentials storeCredentials;
  private Disposable authDisposable;

  @Inject
  public AuthorizePresenter(
      GetCallbackUrl getCallbackUrl,
      DoAuthenticate doAuthenticate,
      StoreCredentials storeCredentials
  ) {
    this.getCallbackUrl = getCallbackUrl;
    this.doAuthenticate = doAuthenticate;
    this.storeCredentials = storeCredentials;
  }

  @Override
  public void onCreate(AuthorizeView view) {
    super.onCreate(view);
    getAndSetCallbackUrl();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (authDisposable != null) {
      authDisposable.dispose();
    }
  }

  void onAuthUrlLoaded(String url) {
    viewOpenUrl(url);
  }

  void onCallbackUrlDetect(String populated) {
    parsePopluatedUrlAndDoAuth(populated);
  }

  private void getAndSetCallbackUrl() {
    String callbackUrl = getCallbackUrl.run();
    viewSetCallbackUrl(callbackUrl);
  }

  private void parsePopluatedUrlAndDoAuth(String populated) {
    String reqToken = parseRequestToken(populated);
    String verifier = parseVerifierToken(populated);
    Timber.d("parsePopluatedUrlAndDoAuth: token = %s, verifier = %s", reqToken, verifier);
    DoAuthenticate.DoAuthenticateArgs args = new DoAuthenticate.DoAuthenticateArgs(reqToken, verifier);
    authDisposable = doAuthenticate.rx(args).subscribe(
        new Consumer<TwitterCredentials>() {
          @Override
          public void accept(TwitterCredentials credentials) throws Exception {
            Timber.d("auth success - storing %s", credentials);
            storeCredentials.run(credentials);
            viewClose();
          }
        },
        new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            Timber.e(throwable, "auth failed");
          }
        }
    );
  }

  private String parseRequestToken(String populated) {
    Pattern pattern = Pattern.compile("\\?oauth_token=(.*)&");
    Matcher matcher = pattern.matcher(populated);
    if (!matcher.find()) { return ""; }
    return matcher.group(1);
  }

  private String parseVerifierToken(String populated) {
    Pattern pattern = Pattern.compile("&oauth_verifier=(.*)");
    Matcher matcher = pattern.matcher(populated);
    if (!matcher.find()) { return ""; }
    return matcher.group(1);
  }

  private void viewOpenUrl(String url) {
    if (view == null) {
      warnViewNull("viewOpenUrl");
      return;
    }
    logViewAction("opening url: %s", url);
    view.openUrl(url);
  }

  private void viewSetCallbackUrl(String url) {
    if (view == null) {
      warnViewNull("viewSetCallbackUrl");
      return;
    }
    logViewAction("setting callback url: %s", url);
    view.setCallbackUrl(url);
  }

  private void viewClose() {
    if (view == null) {
      warnViewNull("viewClose");
      return;
    }
    logViewAction("closing");
    view.close();
  }
}
