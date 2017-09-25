package com.masterjefferson.flockulator.frameworks.t4j

import android.content.Context

import com.masterjefferson.flockulator.R
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterAuthenticator
import com.masterjefferson.flockulator.frameworks.auth.T4JTwitterAuthenticator

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
@Module
class T4JModule {

    @Provides
    @Singleton
    internal fun provideTwitterAuthenticator(context: Context): TwitterAuthenticator {
        val consumerKey = context.getString(R.string.twitter_oauth_consumer_key)
        val consumerSecret = context.getString(R.string.twitter_oauth_consumer_secret)
        return T4JTwitterAuthenticator(consumerKey, consumerSecret)
    }


}
