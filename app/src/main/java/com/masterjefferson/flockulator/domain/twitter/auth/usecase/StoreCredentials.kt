package com.masterjefferson.flockulator.domain.twitter.auth.usecase

import com.masterjefferson.flockulator.domain.UseCase
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

class StoreCredentials(private val store: TwitterCredentialStore) : UseCase<TwitterCredentials, Void>() {

    override fun run(args: TwitterCredentials?): Void? {
        store.storeCredentials(args!!)
        return null
    }
}
