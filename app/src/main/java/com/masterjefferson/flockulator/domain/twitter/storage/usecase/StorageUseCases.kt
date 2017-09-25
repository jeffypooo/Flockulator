package com.masterjefferson.flockulator.domain.twitter.storage.usecase

import com.masterjefferson.flockulator.domain.RxUseCase
import com.masterjefferson.flockulator.domain.timeBlock
import com.masterjefferson.flockulator.domain.twitter.model.BasicTweet
import com.masterjefferson.flockulator.domain.twitter.storage.TweetStore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * ${FILE_NAME}
 * Created by jeff on 9/24/17.
 */
open abstract class TweetStoreUseCase<A, R>(protected val store: TweetStore) : RxUseCase<A, R>()

class TweetCount(store: TweetStore) : TweetStoreUseCase<Void, Int>(store) {
  override fun rx(args: Void?): Observable<Int> {
    return store.tweetCount
  }
}

class AllTweets(store: TweetStore) : TweetStoreUseCase<AllTweets.Args, List<BasicTweet>>(store) {

  data class Args(val before: Date, val after: Date)

  override fun rx(args: Args?): Observable<List<BasicTweet>> {
    return if (args != null) {
      store.all(after = args.after, before = args.before)
    } else {
      store.all()
    }
  }
}

class StoreTweets(store: TweetStore) : TweetStoreUseCase<List<BasicTweet>, Unit>(store) {
  override fun rx(args: List<BasicTweet>?): Observable<Unit> {
    return rxCompletable(args).toObservable()
  }

  override fun rxSingle(args: List<BasicTweet>?): Single<Unit> {
    return rxCompletable(args).toSingle { }
  }

  override fun rxCompletable(args: List<BasicTweet>?): Completable {
    return if (args != null) {
      store.store(args)
    } else {
      Completable.complete()
    }
  }
}

class TopMention(store: TweetStore) : TweetStoreUseCase<Void, TopMention.Result>(store) {

  data class Result(val mention: String, val count: Int)

  override fun rx(args: Void?): Observable<Result> {
    return store.all()
        .sample(5, TimeUnit.SECONDS)
        .observeOn(Schedulers.computation())
        .map { it.flatMap { it.mentions() ?: emptyList() } }
        .filter { !it.isEmpty() }
        .map { findTop(it) }
  }

  private fun findTop(mentions: List<String>): Result {
    val sorted = mentions.groupingBy { it }.eachCount().entries.sortedBy { it.value }
    return Result(sorted.last().key, sorted.last().value)
  }
}

class TopHashTag(store: TweetStore) : TweetStoreUseCase<TopHashTag.Args, List<TopHashTag.Result>>(
    store
) {

  data class Args(val count: Int)

  data class Result(val hashtag: String, val count: Int)

  override fun rx(args: Args?): Observable<List<Result>> {
    return store.all()
        .sample(5, TimeUnit.SECONDS)
        .observeOn(Schedulers.computation())
        .map { it.flatMap { it.hashTags() ?: emptyList() } }
        .filter { !it.isEmpty() }
        .map { findTop(it, args?.count ?: 1) }
  }

  private fun findTop(hashTags: List<String>, count: Int): List<Result> {
    var sorted: List<Map.Entry<String, Int>> = emptyList()
    timeBlock("count and sort ${hashTags.size} hastags") {
      sorted = hashTags.groupingBy { it }.eachCount().entries.sortedBy { it.value }
    }
    return sorted.subList(sorted.size - count, sorted.size).map { Result(it.key, it.value) }
  }
}