package com.masterjefferson.flockulator.domain.twitter.auth.usecase

import com.masterjefferson.flockulator.domain.RxUseCase
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials

import io.reactivex.Observable

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

class DoAuthenticate(private val authenticator: TwitterAuthenticator) : RxUseCase<DoAuthenticate.DoAuthenticateArgs, TwitterCredentials>() {

    override fun rx(args: DoAuthenticateArgs?): Observable<TwitterCredentials> {
        return authenticator.authenticate(args!!.requestToken, args.verifier)
    }

    class DoAuthenticateArgs(val requestToken: String, val verifier: String)

}
