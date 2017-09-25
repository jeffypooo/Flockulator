package com.masterjefferson.flockulator.frameworks.auth

import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

class T4JTwitterAuthenticator(consumerKey: String, consumerSecret: String) : TwitterAuthenticator {
    private val apiFactory: TwitterFactory

    override val callbackUrl: String
        get() = CALLBACK_URL

    private var twitter: Twitter? = null

    private val apiInstance: Twitter
        get() {
            if (twitter == null) {
                twitter = apiFactory.instance
            }
            return twitter!!
        }

    init {
        val conf = ConfigurationBuilder()
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .build()
        this.apiFactory = TwitterFactory(conf)
    }

    override fun fetchAuthorizationURL(): Observable<String> {
        val urlFetcher = Observable.create(
                ObservableOnSubscribe<String> { e ->
                    val authUrl = apiInstance.getOAuthRequestToken(CALLBACK_URL).authorizationURL
                    e.onNext(authUrl)
                    e.onComplete()
                })
        return urlFetcher.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun authenticate(requestToken: String, verificationToken: String): Observable<TwitterCredentials> {
        val authObs = Observable.create(ObservableOnSubscribe<TwitterCredentials> { e ->
            val accessToken = apiInstance.getOAuthAccessToken(verificationToken)
            val credentials = TwitterCredentials(
                    accessToken.userId,
                    accessToken.token,
                    accessToken.tokenSecret
            )
            e.onNext(credentials)
            e.onComplete()
        })
        return authObs.subscribeOn(Schedulers.io())
    }

    companion object {
        private val CALLBACK_URL = "http://flock-me"
    }
}
