package com.masterjefferson.flockulator.domain.twitter.auth

import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.DoAuthenticate
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.FetchAuthUrl
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.GetCallbackUrl
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.GetCredentials
import com.masterjefferson.flockulator.domain.twitter.auth.usecase.StoreCredentials

import dagger.Module
import dagger.Provides

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
@Module
class AuthModule(private val authenticator: TwitterAuthenticator, private val credentialStore: TwitterCredentialStore) {

    @Provides
    internal fun provideFetchAuthUrl(): FetchAuthUrl {
        return FetchAuthUrl(authenticator)
    }

    @Provides
    internal fun provideGetCallbackUrl(): GetCallbackUrl {
        return GetCallbackUrl(authenticator)
    }

    @Provides
    internal fun provideDoAuthenticate(): DoAuthenticate {
        return DoAuthenticate(authenticator)
    }

    @Provides
    internal fun provideStoreCredentials(): StoreCredentials {
        return StoreCredentials(credentialStore)
    }

    @Provides
    internal fun provideGetCredentials(): GetCredentials {
        return GetCredentials(credentialStore)
    }

}
