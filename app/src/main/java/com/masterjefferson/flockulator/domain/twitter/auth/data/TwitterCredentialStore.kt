package com.masterjefferson.flockulator.domain.twitter.auth.data

import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

interface TwitterCredentialStore {
    val consumerKey: String
    val consumerSecret: String
    val credentials: TwitterCredentials?
    fun storeCredentials(credentials: TwitterCredentials)
}
