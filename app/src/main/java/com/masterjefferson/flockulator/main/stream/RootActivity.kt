package com.masterjefferson.flockulator.main.stream

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.masterjefferson.flockulator.R
import com.masterjefferson.flockulator.app.FlockulatorApp
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore
import com.masterjefferson.flockulator.domain.twitter.model.BasicTweet
import com.masterjefferson.flockulator.domain.twitter.storage.TweetStore
import com.masterjefferson.flockulator.domain.twitter.storage.usecase.TopHashTag
import com.masterjefferson.flockulator.domain.twitter.storage.usecase.TopMention
import com.masterjefferson.flockulator.domain.twitter.stream.data.TwitterStreamingApi
import com.masterjefferson.flockulator.frameworks.stream.T4JTwitterStreamingApi
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RootActivity : AppCompatActivity() {

  @Inject internal lateinit var credentialStore: TwitterCredentialStore
  @Inject internal lateinit var tweetStore: TweetStore
  private val disposables = CompositeDisposable()
  private lateinit var api: TwitterStreamingApi

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    configure()
    api = T4JTwitterStreamingApi(credentialStore)
    val tweetsDisp = api.sample()
        .buffer(1, TimeUnit.SECONDS)
        .subscribe(
            { store(it) },
            { Timber.e(it, "error in tweet stream") }
        )
    val topMentions = TopMention(tweetStore)
    val topHashtags = TopHashTag(tweetStore)
//    val topMention = topMentions.rx()
//        .subscribe(
//            { Timber.i("top mention = @${it.mention} (${it.count})") },
//            { Timber.e(it) }
//        )
    val topTag = topHashtags.rx(TopHashTag.Args(5))
        .subscribe(
            { Timber.i("top hashtags = $it") },
            { Timber.e(it) }
        )
    val tweetCount = tweetStore.tweetCount.subscribe(
        { Timber.i("tweet count = $it") },
        { Timber.e(it) }
    )
//    val hashTagStream = api.sample()
//        .filter { it.hashTags() != null }
//        .flatMap { Observable.fromIterable(it.hashTags()!!) }
//        .subscribe(
//            {Timber.d("#$it")},
//            {Timber.e(it)}
//        )
    disposables.addAll(tweetsDisp, topTag, tweetCount)
  }

  override fun onDestroy() {
    super.onDestroy()
    disposables.clear()
  }

  private fun configure() {
    configureViews()
    (application as FlockulatorApp).getAppComponent().inject(rootActivity = this)
  }

  private fun configureViews() {
    setContentView(R.layout.activity_dashboard)
  }

  private fun store(tweets: List<BasicTweet>) {
    val immutable = ArrayList(tweets)
    val storeDisp = tweetStore.store(immutable).subscribe(
        {  },
        { Timber.e(it, "error storing tweets") }
    )
    disposables.add(storeDisp)
  }
}
