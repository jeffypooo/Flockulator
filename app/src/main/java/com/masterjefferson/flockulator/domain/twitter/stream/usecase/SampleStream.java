package com.masterjefferson.flockulator.domain.twitter.stream.usecase;

import com.masterjefferson.flockulator.domain.RxUseCase;
import com.masterjefferson.flockulator.domain.twitter.model.BasicTweet;
import com.masterjefferson.flockulator.domain.twitter.stream.data.TwitterStreamingApi;

import io.reactivex.Observable;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public class SampleStream
    extends RxUseCase<Void, BasicTweet> {

  private final TwitterStreamingApi api;

  public SampleStream(TwitterStreamingApi api) {this.api = api;}

  @Override
  public Observable<BasicTweet> rx(Void args) {
    return api.sample();
  }
}
