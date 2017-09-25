package com.masterjefferson.flockulator.domain.twitter.storage

import com.masterjefferson.flockulator.domain.twitter.model.BasicTweet
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

/**
 * ${FILE_NAME}
 * Created by jeff on 9/24/17.
 */
interface TweetStore {
  val tweetCount: Observable<Int>
  fun all(): Observable<List<BasicTweet>>
  fun all(after: Date, before: Date = Date()): Observable<List<BasicTweet>>
  fun store(tweet: BasicTweet): Completable
  fun store(tweets: List<BasicTweet>): Completable
}
