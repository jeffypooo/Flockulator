package com.masterjefferson.flockulator.domain.twitter.stream.usecase

import com.masterjefferson.flockulator.domain.RxUseCase
import com.masterjefferson.flockulator.domain.twitter.model.BasicTweet
import com.masterjefferson.flockulator.domain.twitter.stream.data.TwitterStreamingApi

import io.reactivex.Observable

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

class SampleStream(private val api: TwitterStreamingApi) : RxUseCase<Void, BasicTweet>() {

    override fun rx(args: Void?): Observable<BasicTweet> {
        return api.sample()
    }
}
