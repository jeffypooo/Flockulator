package com.masterjefferson.flockulator.onboard.welcome

import com.masterjefferson.flockulator.FlockulatorUnitTest
import com.masterjefferson.flockulator.domain.twitter.auth.AuthModule
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials
import io.reactivex.Observable
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
class WelcomePresenterTest : FlockulatorUnitTest() {

  @Mock internal lateinit var mockAuthenticator: TwitterAuthenticator
  @Mock internal lateinit var mockStore: TwitterCredentialStore
  @Mock internal lateinit var mockView: WelcomeView
  internal lateinit var presenter: WelcomePresenter

  override fun setup() {
    super.setup()
    presenter = DaggerWelcomeComponent.builder()
        .authModule(AuthModule(mockAuthenticator, mockStore))
        .build()
        .presenter()
  }

  @Test
  fun onAuthorizeClick_success() {
    `when`(mockAuthenticator.fetchAuthorizationURL()).thenReturn(Observable.just("google.com"))
    presenter.onCreate(mockView)
    presenter.onAuthorizeClick()
    verify<WelcomeView>(mockView).setLoading(true)
    verify<TwitterAuthenticator>(mockAuthenticator).fetchAuthorizationURL()
    verify<WelcomeView>(mockView).setLoading(false)
    verify<WelcomeView>(mockView).openAuthorizationPage("google.com")
  }

  @Test
  fun onResume_noCredentials_success() {
    presenter.onCreate(mockView)
    presenter.onResume()
    verify<TwitterCredentialStore>(mockStore).credentials
    verifyZeroInteractions(mockView)
  }

  @Test
  fun onResume_existingCredentials_success() {
    `when`<TwitterCredentials>(mockStore.credentials).thenReturn(
        TwitterCredentials(
            123,
            "token",
            "secret"
        )
    )
    presenter.onCreate(mockView)
    presenter.onResume()
    verify<WelcomeView>(mockView).proceedToApplication()
  }

  @Test
  fun onDestroy_viewIsReleased() {
    `when`(mockAuthenticator.fetchAuthorizationURL()).thenReturn(Observable.just("google.com"))
    presenter.onCreate(mockView)
    presenter.onDestroy()
    presenter.onAuthorizeClick()
    verifyZeroInteractions(mockView)
  }
}
