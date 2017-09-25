package com.masterjefferson.flockulator.domain.twitter.stream.data

import com.masterjefferson.flockulator.domain.twitter.model.BasicTweet

import io.reactivex.Observable

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

interface TwitterStreamingApi {

    fun sample(): Observable<BasicTweet>

}
