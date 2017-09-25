package com.masterjefferson.flockulator.onboard.welcome

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.masterjefferson.flockulator.R
import com.masterjefferson.flockulator.app.FlockulatorApp
import com.masterjefferson.flockulator.domain.twitter.auth.AuthModule
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore
import com.masterjefferson.flockulator.main.stream.RootActivity
import com.masterjefferson.flockulator.onboard.authorize.AuthorizeActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import javax.inject.Inject

class WelcomeActivity : AppCompatActivity(), WelcomeView {


  @Inject internal lateinit var twitterAuthenticator: TwitterAuthenticator
  @Inject internal lateinit var credentialStore: TwitterCredentialStore
  private lateinit var presenter: WelcomePresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    configure()
    presenter.onCreate(this)
  }

  override fun onResume() {
    super.onResume()
    presenter.onResume()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  override fun openAuthorizationPage(url: String) {
    val intent = Intent(this, AuthorizeActivity::class.java)
    intent.putExtra(AuthorizeActivity.EXTRA_AUTH_URL, url)
    startActivity(intent)
  }

  override fun setLoading(loading: Boolean) {
    this.authButton.isEnabled = !loading
    val opacity = if (loading) 0.5f else 1f
    this.authButton.animate()
        .alpha(opacity)
        .setDuration(250)
        .start()
    this.loadingIndicator.visibility = if (loading) View.VISIBLE else View.GONE
  }

  override fun proceedToApplication() {
    val intent = Intent(this, RootActivity::class.java)
    startActivity(intent)
    finish()
  }

  private fun configure() {
    configureViews()
    configureDependencies()
  }

  private fun configureViews() {
    setContentView(R.layout.activity_welcome)
    this.authButton.setOnClickListener { presenter.onAuthorizeClick() }
  }

  private fun configureDependencies() {
    (application as FlockulatorApp).getAppComponent().inject(this)
    this.presenter = DaggerWelcomeComponent.builder()
        .authModule(AuthModule(twitterAuthenticator, credentialStore))
        .build()
        .presenter()
  }
}
