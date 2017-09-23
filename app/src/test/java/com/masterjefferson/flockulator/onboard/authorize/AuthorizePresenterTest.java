package com.masterjefferson.flockulator.onboard.authorize;

import com.masterjefferson.flockulator.FlockulatorUnitTest;
import com.masterjefferson.flockulator.domain.twitter.auth.AuthModule;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore;
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;

import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Observable;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public class AuthorizePresenterTest
    extends FlockulatorUnitTest {
  private static final TwitterCredentials TEST_CREDS                  = new TwitterCredentials(123, "token", "secret");
  private static final String             TEST_CALLBACK_URL           = "http://foo";
  private static final String             TEST_CALLBACK_URL_TOKEN     = "1Qj5_QAAAAAA2S0OAAABXqvZfMo";
  private static final String             TEST_CALLBACK_URL_VERIFIER  = "Fq3em965zgr1swZmvbCantFiaYvs8ALB";
  private static final String             TEST_CALLBACK_URL_POPULATED =
      "http://foo/?oauth_token=" + TEST_CALLBACK_URL_TOKEN + "&oauth_verifier=" + TEST_CALLBACK_URL_VERIFIER;

  @Mock TwitterAuthenticator   mockAuthenticator;
  @Mock TwitterCredentialStore mockStore;
  @Mock AuthorizeView          mockView;

  private AuthorizePresenter presenter;

  @Override
  public void setup() {
    super.setup();
    configureMocks();
    configurePresenter();
  }

  @Test
  public void onCreate_success() {
    presenter.onCreate(mockView);
    verify(mockView).setCallbackUrl(TEST_CALLBACK_URL);
  }

  @Test
  public void onAuthUrlLoaded_success() {
    checkOnCreate();
    presenter.onAuthUrlLoaded("google.com");
    verify(mockView).openUrl("google.com");
  }

  @Test
  public void onCallbackUrlDetect_success() {
    checkOnCreate();
    presenter.onCallbackUrlDetect(TEST_CALLBACK_URL_POPULATED);
    verify(mockAuthenticator, timeout(100)).authenticate(TEST_CALLBACK_URL_TOKEN, TEST_CALLBACK_URL_VERIFIER);
    verify(mockStore).storeCredentials(TEST_CREDS);
    verify(mockView).close();
  }

  private void configureMocks() {
    when(mockAuthenticator.getCallbackUrl()).thenReturn(TEST_CALLBACK_URL);
    when(mockAuthenticator.authenticate(TEST_CALLBACK_URL_TOKEN, TEST_CALLBACK_URL_VERIFIER))
        .thenReturn(Observable.just(TEST_CREDS));
  }

  private void configurePresenter() {
    this.presenter = DaggerAuthorizeComponent.builder()
        .authModule(new AuthModule(mockAuthenticator, mockStore))
        .build()
        .presenter();
  }

  private void checkOnCreate() {
    presenter.onCreate(mockView);
    verify(mockView).setCallbackUrl(TEST_CALLBACK_URL);
  }

}
