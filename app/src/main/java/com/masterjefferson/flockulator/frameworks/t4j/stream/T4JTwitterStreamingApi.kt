package com.masterjefferson.flockulator.frameworks.stream

import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore
import com.masterjefferson.flockulator.domain.twitter.model.BasicTweet
import com.masterjefferson.flockulator.domain.twitter.stream.data.TwitterStreamingApi
import com.masterjefferson.flockulator.frameworks.t4j.T4JCompat
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import twitter4j.*
import twitter4j.conf.ConfigurationBuilder
import java.lang.Exception

class StatusListenerDisposable(private val api: TwitterStream,
                               private val listener: StatusListener) : Disposable {

  private var disposed = false

  override fun dispose() {
    T4JCompat.removeStatusListener(api, listener)
    disposed = true
  }

  override fun isDisposed(): Boolean {
    return disposed
  }
}

abstract class BasicStatusListener : StatusListener {
  override fun onTrackLimitationNotice(numberOfLimitedStatuses: Int) {
    Timber.v("track limitation notice: $numberOfLimitedStatuses")
  }

  override fun onStallWarning(warning: StallWarning?) {
    warning?.let { Timber.w("stall warning: $it") }
  }

  override fun onDeletionNotice(statusDeletionNotice: StatusDeletionNotice?) {
//    statusDeletionNotice?.let { Timber.v("deletion notice: $it") }
  }

  override fun onScrubGeo(userId: Long, upToStatusId: Long) {
    Timber.v("scrub geo: $userId, $upToStatusId")
  }
}

fun basicTweet(from: Status): BasicTweet {
  return BasicTweet(from.id, from.createdAt, from.user.name, from.text)
}

class T4JTwitterStreamingApi(private val credentialStore: TwitterCredentialStore) :
    TwitterStreamingApi {

  private var twitterStream: TwitterStream? = null
  private var isRunning = false
  private val apiInstance: TwitterStream
    get() {
      if (twitterStream == null) {
        val confBuilder = ConfigurationBuilder()
            .setAsyncNumThreads(4)
            .setOAuthConsumerKey(credentialStore.consumerKey)
            .setOAuthConsumerSecret(credentialStore.consumerSecret);
        credentialStore.credentials?.let {
          confBuilder.setOAuthAccessToken(it.accessToken)
          confBuilder.setOAuthAccessTokenSecret(it.tokenSecret)
        }
        twitterStream = TwitterStreamFactory(confBuilder.build()).instance
      }
      return twitterStream!!
    }

  override fun sample(): Observable<BasicTweet> {
    val sampleObs: Observable<BasicTweet> = Observable.create { e ->
      val api = apiInstance
      val listener = object : BasicStatusListener() {
        override fun onException(ex: Exception?) {
          ex?.let { e.onError(it) }
        }

        override fun onStatus(status: Status?) {
          status?.let { e.onNext(basicTweet(from = status)) }
        }
      }
      e.setDisposable(StatusListenerDisposable(api, listener))
      T4JCompat.addStatusListener(api, listener)
      if (!isRunning) {
        val query = FilterQuery()
        query.track("taketheknee", "trump", "WhiteHouse", "GOP", "politics", "NFL", "UN", "UnitedNations")
        api.filter(query)
        isRunning = true
      }
    }
    return sampleObs.subscribeOn(Schedulers.io())
  }
}
