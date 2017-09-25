package com.masterjefferson.flockulator.domain.twitter.storage

import com.masterjefferson.flockulator.domain.twitter.storage.usecase.AllTweets
import com.masterjefferson.flockulator.domain.twitter.storage.usecase.TweetCount
import dagger.Module
import dagger.Provides

/**
 * ${FILE_NAME}
 * Created by jeff on 9/24/17.
 */
@Module
class StorageModule(private val store: TweetStore) {

  @Provides
  fun provideTweetCount(): TweetCount {
    return TweetCount(store)
  }

  @Provides
  fun provideAllTweets(): AllTweets {
    return AllTweets(store)
  }
}