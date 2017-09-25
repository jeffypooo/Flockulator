package com.masterjefferson.flockulator.onboard.authorize

import com.masterjefferson.flockulator.app.FlockulatorActivityPresenter
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.DoAuthenticate
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.GetCallbackUrl
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.StoreCredentials

import java.util.regex.Matcher
import java.util.regex.Pattern

import javax.inject.Inject

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import timber.log.Timber

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

class AuthorizePresenter @Inject
constructor(
        private val getCallbackUrl: GetCallbackUrl,
        private val doAuthenticate: DoAuthenticate,
        private val storeCredentials: StoreCredentials
) : FlockulatorActivityPresenter<AuthorizeView>() {
    private var authDisposable: Disposable? = null

    override fun onCreate(view: AuthorizeView) {
        super.onCreate(view)
        getAndSetCallbackUrl()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (authDisposable != null) {
            authDisposable!!.dispose()
        }
    }

    fun onAuthUrlLoaded(url: String) {
        viewOpenUrl(url)
    }

    fun onCallbackUrlDetect(populated: String) {
        parsePopluatedUrlAndDoAuth(populated)
    }

    private fun getAndSetCallbackUrl() {
        val callbackUrl = getCallbackUrl.run()
        viewSetCallbackUrl(callbackUrl)
    }

    private fun parsePopluatedUrlAndDoAuth(populated: String) {
        val reqToken = parseRequestToken(populated)
        val verifier = parseVerifierToken(populated)
        Timber.d("parsePopluatedUrlAndDoAuth: token = %s, verifier = %s", reqToken, verifier)
        val args = DoAuthenticate.DoAuthenticateArgs(reqToken, verifier)
        authDisposable = doAuthenticate.rx(args).subscribe(
                { credentials ->
                    Timber.d("auth success - storing %s", credentials)
                    storeCredentials.run(credentials)
                    viewClose()
                }
        ) { throwable -> Timber.e(throwable, "auth failed") }
    }

    private fun parseRequestToken(populated: String): String {
        val pattern = Pattern.compile("\\?oauth_token=(.*)&")
        val matcher = pattern.matcher(populated)
        return if (!matcher.find()) {
            ""
        } else matcher.group(1)
    }

    private fun parseVerifierToken(populated: String): String {
        val pattern = Pattern.compile("&oauth_verifier=(.*)")
        val matcher = pattern.matcher(populated)
        return if (!matcher.find()) {
            ""
        } else matcher.group(1)
    }

    private fun viewOpenUrl(url: String) {
        if (view == null) {
            warnViewNull("viewOpenUrl")
            return
        }
        logViewAction("opening url: %s", url)
        view!!.openUrl(url)
    }

    private fun viewSetCallbackUrl(url: String?) {
        if (view == null) {
            warnViewNull("viewSetCallbackUrl")
            return
        }
        logViewAction("setting callback url: %s", url!!)
        view!!.setCallbackUrl(url)
    }

    private fun viewClose() {
        if (view == null) {
            warnViewNull("viewClose")
            return
        }
        logViewAction("closing")
        view!!.close()
    }
}
