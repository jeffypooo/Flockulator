package com.masterjefferson.flockulator.onboard.authorize

import com.masterjefferson.flockulator.FlockulatorUnitTest
import com.masterjefferson.flockulator.domain.twitter.auth.AuthModule
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials
import io.reactivex.Observable
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.timeout
import org.mockito.Mockito.verify

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */
class AuthorizePresenterTest : FlockulatorUnitTest() {

  @Mock internal lateinit var mockAuthenticator: TwitterAuthenticator
  @Mock internal lateinit var mockStore: TwitterCredentialStore
  @Mock internal lateinit var mockView: AuthorizeView
  private lateinit var presenter: AuthorizePresenter

  override fun setup() {
    super.setup()
    configureMocks()
    configurePresenter()
  }

  @Test
  fun onCreate_success() {
    presenter.onCreate(mockView)
    verify<AuthorizeView>(mockView).setCallbackUrl(TEST_CALLBACK_URL)
  }

  @Test
  fun onAuthUrlLoaded_success() {
    checkOnCreate()
    presenter.onAuthUrlLoaded("google.com")
    verify<AuthorizeView>(mockView).openUrl("google.com")
  }

  @Test
  fun onCallbackUrlDetect_success() {
    checkOnCreate()
    presenter.onCallbackUrlDetect(TEST_CALLBACK_URL_POPULATED)
    verify<TwitterAuthenticator>(mockAuthenticator, timeout(100)).authenticate(
        TEST_CALLBACK_URL_TOKEN,
        TEST_CALLBACK_URL_VERIFIER
    )
    verify<TwitterCredentialStore>(mockStore).storeCredentials(TEST_CREDS)
    verify<AuthorizeView>(mockView).close()
  }

  private fun configureMocks() {
    `when`(mockAuthenticator.callbackUrl).thenReturn(TEST_CALLBACK_URL)
    `when`(mockAuthenticator.authenticate(TEST_CALLBACK_URL_TOKEN, TEST_CALLBACK_URL_VERIFIER))
        .thenReturn(Observable.just(TEST_CREDS))
  }

  private fun configurePresenter() {
    this.presenter = DaggerAuthorizeComponent.builder()
        .authModule(AuthModule(mockAuthenticator, mockStore))
        .build()
        .presenter()
  }

  private fun checkOnCreate() {
    presenter.onCreate(mockView)
    verify<AuthorizeView>(mockView).setCallbackUrl(TEST_CALLBACK_URL)
  }

  companion object {
    private val TEST_CREDS = TwitterCredentials(123, "token", "secret")
    private val TEST_CALLBACK_URL = "http://foo"
    private val TEST_CALLBACK_URL_TOKEN = "1Qj5_QAAAAAA2S0OAAABXqvZfMo"
    private val TEST_CALLBACK_URL_VERIFIER = "Fq3em965zgr1swZmvbCantFiaYvs8ALB"
    private val TEST_CALLBACK_URL_POPULATED =
        "http://foo/?oauth_token=$TEST_CALLBACK_URL_TOKEN&oauth_verifier=$TEST_CALLBACK_URL_VERIFIER"
  }
}
