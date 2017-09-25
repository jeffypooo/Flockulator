package com.masterjefferson.flockulator.domain.twitter.auth.usecase

import com.masterjefferson.flockulator.domain.UseCase
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

class GetCredentials(private val store: TwitterCredentialStore) : UseCase<Void, TwitterCredentials>() {

    override fun run(args: Void?): TwitterCredentials? {
        return store.credentials
    }
}
