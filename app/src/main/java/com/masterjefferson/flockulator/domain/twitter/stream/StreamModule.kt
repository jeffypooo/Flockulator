package com.masterjefferson.flockulator.domain.twitter.stream

import com.masterjefferson.flockulator.domain.twitter.stream.data.TwitterStreamingApi
import com.masterjefferson.flockulator.domain.twitter.stream.usecase.SampleStream

import dagger.Module
import dagger.Provides

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */
@Module
class StreamModule(private val streamingApi: TwitterStreamingApi) {

    @Provides
    internal fun provideSampleStream(): SampleStream {
        return SampleStream(streamingApi)
    }

}
