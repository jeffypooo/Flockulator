package com.masterjefferson.flockulator.domain.twitter.stream;

import com.masterjefferson.flockulator.domain.twitter.stream.data.TwitterStreamingApi;
import com.masterjefferson.flockulator.domain.twitter.stream.usecase.SampleStream;

import dagger.Module;
import dagger.Provides;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */
@Module
public class StreamModule {

  private final TwitterStreamingApi streamingApi;

  public StreamModule(TwitterStreamingApi streamingApi) {this.streamingApi = streamingApi;}

  @Provides
  SampleStream provideSampleStream() { return new SampleStream(streamingApi); }

}
