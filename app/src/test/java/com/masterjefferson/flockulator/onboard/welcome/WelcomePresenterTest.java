package com.masterjefferson.flockulator.onboard.welcome;

import com.masterjefferson.flockulator.FlockulatorUnitTest;
import com.masterjefferson.flockulator.domain.twitter.auth.AuthModule;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator;
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore;
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials;

import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public class WelcomePresenterTest
    extends FlockulatorUnitTest {
  @Mock TwitterAuthenticator   mockAuthenticator;
  @Mock TwitterCredentialStore mockStore;
  @Mock WelcomeView            mockView;
  WelcomePresenter presenter;

  @Override
  public void setup() {
    super.setup();
    presenter = DaggerWelcomeComponent.builder()
        .authModule(new AuthModule(mockAuthenticator, mockStore))
        .build()
        .presenter();
  }

  @Test
  public void onAuthorizeClick_success() {
    when(mockAuthenticator.fetchAuthorizationURL()).thenReturn(Observable.just("google.com"));
    presenter.onCreate(mockView);
    presenter.onAuthorizeClick();
    verify(mockView).setLoading(true);
    verify(mockAuthenticator).fetchAuthorizationURL();
    verify(mockView).setLoading(false);
    verify(mockView).openAuthorizationPage("google.com");
  }

  @Test
  public void onResume_noCredentials_success() {
    presenter.onCreate(mockView);
    presenter.onResume();
    verify(mockStore).getCredentials();
    verifyZeroInteractions(mockView);
  }

  @Test
  public void onResume_existingCredentials_success() {
    when(mockStore.getCredentials()).thenReturn(new TwitterCredentials(123, "token", "secret"));
    presenter.onCreate(mockView);
    presenter.onResume();
    verify(mockView).proceedToApplication();
  }



  @Test
  public void onDestroy_viewIsReleased() {
    when(mockAuthenticator.fetchAuthorizationURL()).thenReturn(Observable.just("google.com"));
    presenter.onCreate(mockView);
    presenter.onDestroy();
    presenter.onAuthorizeClick();
    verifyZeroInteractions(mockView);
  }
}
