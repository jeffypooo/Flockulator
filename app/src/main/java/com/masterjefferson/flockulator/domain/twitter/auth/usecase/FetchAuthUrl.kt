package com.masterjefferson.flockulator.domain.twitter.auth.usecase

import com.masterjefferson.flockulator.domain.RxUseCase
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator

import io.reactivex.Observable

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

class FetchAuthUrl(private val authenticator: TwitterAuthenticator) : RxUseCase<Void, String>() {

    override fun rx(args: Void?): Observable<String> {
        return authenticator.fetchAuthorizationURL()
    }
}
