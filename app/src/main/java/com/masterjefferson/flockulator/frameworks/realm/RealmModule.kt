package com.masterjefferson.flockulator.frameworks.realm

import com.masterjefferson.flockulator.domain.twitter.storage.TweetStore
import com.masterjefferson.flockulator.frameworks.realm.storage.RealmTweetStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * ${FILE_NAME}
 * Created by jeff on 9/25/17.
 */
@Module
class RealmModule {

  @Provides
  @Singleton
  fun provideTweetStore(): TweetStore {
    return RealmTweetStore()
  }
}