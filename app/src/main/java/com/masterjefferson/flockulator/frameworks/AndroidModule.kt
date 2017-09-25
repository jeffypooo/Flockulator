package com.masterjefferson.flockulator.frameworks

import android.content.Context

import com.masterjefferson.flockulator.frameworks.auth.SharedPrefsCredentialStore
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore

import dagger.Module
import dagger.Provides

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */
@Module
class AndroidModule {

    @Provides internal fun provideTwitterCredentialStore(context: Context): TwitterCredentialStore {
        return SharedPrefsCredentialStore(context)
    }

}
