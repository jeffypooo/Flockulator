package com.masterjefferson.flockulator.domain.twitter.auth.usecase

import com.masterjefferson.flockulator.domain.UseCase
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

class GetCallbackUrl(private val twitterAuthenticator: TwitterAuthenticator) : UseCase<Void, String>() {

    override fun run(args: Void?): String {
        return twitterAuthenticator.callbackUrl
    }
}
