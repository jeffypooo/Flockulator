package com.masterjefferson.flockulator.frameworks.realm.storage

import android.os.HandlerThread
import com.masterjefferson.flockulator.domain.twitter.model.BasicTweet
import com.masterjefferson.flockulator.domain.twitter.storage.TweetStore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.realm.*
import timber.log.Timber
import java.util.*

/**
 * ${FILE_NAME}
 * Created by jeff on 9/24/17.
 */
class RealmTweetStore : TweetStore {


  private val dbThread: HandlerThread = HandlerThread("TweetStore I/O")
  private val ioScheduler: Scheduler

  init {
    dbThread.start()
    ioScheduler = AndroidSchedulers.from(dbThread.looper)
    ioScheduler.scheduleDirect {
      Realm.getDefaultInstance().executeTransaction {
        realm -> realm.deleteAll()
      }
    }
  }

  override val tweetCount: Observable<Int>
    get() {
      return Observable.create { e ->
        val realm = Realm.getDefaultInstance()
        val results = allTweets(realm).findAll()
        val listener = OrderedRealmCollectionChangeListener<RealmResults<RealmTweet>> { res, _ ->
          e.onNext(res.size)
        }
        results.addChangeListener(listener)
        e.setDisposable(ChangeObservableDisposable(realm, results, listener))
      }
    }

  override fun all(): Observable<List<BasicTweet>> {
    return Observable.defer {
      val res = allTweets(Realm.getDefaultInstance()).findAll()
      return@defer tweetsObservable(res)
    }.subscribeOn(ioScheduler)
  }

  override fun all(after: Date, before: Date): Observable<List<BasicTweet>> {
    return Observable.defer {
      val res = allTweets(Realm.getDefaultInstance())
          .between("timestamp", after, before)
          .findAll()
      return@defer tweetsObservable(res)
    }.subscribeOn(ioScheduler)
  }

  override fun store(tweet: BasicTweet): Completable {
    return Completable.create { emitter ->
      val realmTweet = RealmTweet()
      realmTweet.id = tweet.id
      realmTweet.timestamp = tweet.timestamp
      realmTweet.userName = tweet.userName
      realmTweet.text = tweet.text
      Realm.getDefaultInstance().executeTransaction { realm ->
        realm.copyToRealmOrUpdate(realmTweet)
        realm.close()
      }
      emitter.onComplete()
    }.subscribeOn(ioScheduler)
  }

  override fun store(tweets: List<BasicTweet>): Completable {
    return Completable.create { emitter ->
      val realm = Realm.getDefaultInstance()
      val start = System.currentTimeMillis()
      realm.executeTransaction {
        for (tweet in tweets) {
          val realmTweet = RealmTweet()
          realmTweet.id = tweet.id
          realmTweet.timestamp = tweet.timestamp
          realmTweet.userName = tweet.userName
          realmTweet.text = tweet.text
          realm.copyToRealmOrUpdate(realmTweet)
        }
      }
      realm.close()
      val elasped = System.currentTimeMillis() - start
      Timber.v("batch insertion took $elasped ms")
      emitter.onComplete()
    }.subscribeOn(ioScheduler)
  }

  private fun allTweets(realm: Realm): RealmQuery<RealmTweet> {
    return realm.where(RealmTweet::class.java)
  }

  private fun tweetsObservable(results: RealmResults<RealmTweet>): Observable<List<BasicTweet>> {
    return Observable.create { emitter ->
      val realm = Realm.getDefaultInstance()
      val listener = OrderedRealmCollectionChangeListener<RealmResults<RealmTweet>> { t, _ ->
        emitter.onNext(t.map { BasicTweet(it.id, it.timestamp, it.userName, it.text) })
      }
      results.addChangeListener(listener)
      emitter.setDisposable(ChangeObservableDisposable(realm, results, listener))
    }
  }
}

class ChangeObservableDisposable<E : RealmModel>(
    private val realm: Realm,
    private val results: RealmResults<E>,
    private val listener: OrderedRealmCollectionChangeListener<RealmResults<E>>
) : Disposable {

  var disposed = false
  override fun isDisposed(): Boolean {
    return disposed
  }

  override fun dispose() {
    results.removeChangeListener(listener)
    realm.close()
    disposed = true
  }
}



