package com.masterjefferson.flockulator.onboard.authorize

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.masterjefferson.flockulator.R
import com.masterjefferson.flockulator.app.FlockulatorApp
import com.masterjefferson.flockulator.domain.twitter.auth.AuthModule
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore
import kotlinx.android.synthetic.main.activity_authorize.*
import timber.log.Timber
import javax.inject.Inject

class AuthorizeActivity : AppCompatActivity(), AuthorizeView {

  @Inject
  lateinit internal var twitterAuthenticator: TwitterAuthenticator
  @Inject
  lateinit internal var credentialStore: TwitterCredentialStore
  private lateinit var presenter: AuthorizePresenter
  private var callbackUrlOverride: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    configure()
    presenter.onCreate(this)
    handleIntent()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter!!.onDestroy()
  }

  override fun openUrl(url: String) {
    this.webView.loadUrl(url)
  }

  override fun setCallbackUrl(callbackUrl: String) {
    this.callbackUrlOverride = callbackUrl
  }

  override fun close() {
    finish()
  }

  private fun configure() {
    configureViews()
    configureDependencies()
  }

  private fun configureViews() {
    setContentView(R.layout.activity_authorize)
    this.webView.webViewClient = AuthCallbackClient()
  }

  private fun configureDependencies() {
    (application as FlockulatorApp).getAppComponent().inject(this)
    presenter = DaggerAuthorizeComponent.builder()
        .authModule(AuthModule(twitterAuthenticator, credentialStore))
        .build()
        .presenter()
  }

  private fun handleIntent() {
    val intent = intent
    val authUrl = intent.getStringExtra(EXTRA_AUTH_URL)
    presenter.onAuthUrlLoaded(authUrl)
  }

  private inner class AuthCallbackClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
      val requestUrlStr = request.url.toString()
      if (requestUrlStr.startsWith(callbackUrlOverride!!)) {
        Timber.i("callback url override detected: %s", requestUrlStr)
        presenter!!.onCallbackUrlDetect(requestUrlStr)
        return true
      }
      return super.shouldOverrideUrlLoading(view, request)
    }
  }

  companion object {

    val EXTRA_AUTH_URL = AuthorizeActivity::class.java.canonicalName + ".EXTRA_AUTH_URL"
  }
}
