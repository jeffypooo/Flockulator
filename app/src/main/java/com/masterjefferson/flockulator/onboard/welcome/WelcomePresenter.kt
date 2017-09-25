package com.masterjefferson.flockulator.onboard.welcome

import com.masterjefferson.flockulator.app.FlockulatorActivityPresenter
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.FetchAuthUrl
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.GetCredentials

import javax.inject.Inject

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import timber.log.Timber

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

class WelcomePresenter @Inject
constructor(private val fetchAuthUrl: FetchAuthUrl, private val getCredentials: GetCredentials) : FlockulatorActivityPresenter<WelcomeView>() {
    private var fetchUrlDisposable: Disposable? = null

    override fun onResume() {
        super.onResume()
        checkForExistingCredentials()
    }

    fun onAuthorizeClick() {
        viewSetLoading(true)
        Timber.d("fetching auth url...")
        this.fetchUrlDisposable = fetchAuthUrl.rx()
                .subscribe { url ->
                    viewSetLoading(false)
                    viewOpenAuthorizationPage(url)
                }
    }

    private fun checkForExistingCredentials() {
        val credentials = getCredentials.run()
        if (credentials == null) {
            Timber.d("no existing credentials")
            return
        }
        Timber.d("have existing credentials: %s", credentials)
        viewProceedToApplication()
    }

    private fun viewSetLoading(loading: Boolean) {
        if (view == null) {
            warnViewNull("viewSetLoading")
            return
        }
        logViewAction("set loading %b", loading)
        view!!.setLoading(loading)
    }

    private fun viewOpenAuthorizationPage(url: String) {
        if (view == null) {
            warnViewNull("viewOpenAuthorizationPage")
            return
        }
        logViewAction("opening auth url: %s", url)
        view!!.openAuthorizationPage(url)
    }

    private fun viewProceedToApplication() {
        if (view == null) {
            warnViewNull("viewProceedToApplication")
            return
        }
        logViewAction("proceeding to application")
        view!!.proceedToApplication()
    }
}
