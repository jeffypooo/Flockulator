package com.masterjefferson.flockulator.domain.twitter.auth.data

import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials

import io.reactivex.Observable

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

interface TwitterAuthenticator {

    val callbackUrl: String
    fun fetchAuthorizationURL(): Observable<String>
    fun authenticate(requestToken: String, verificationToken: String): Observable<TwitterCredentials>

}
