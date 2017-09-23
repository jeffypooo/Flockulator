package com.masterjefferson.flockulator.domain;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public abstract class RxUseCase<A, R> {

  public abstract Observable<R> rx(A args);

  public Observable<R> rx() {
    return rx(null);
  }

  public Single<R> rxSingle(A args) {
    return rx(args).take(1).singleOrError();
  }

  public Single<R> rxSingle() {
    return rx().take(1).singleOrError();
  }

  public Completable rxCompletable(A args) {
    return rxSingle(args).toCompletable();
  }

  public Completable rxCompletable() {
    return rxSingle().toCompletable();
  }

  public Iterable<R> blockingExecute(A args) {
    return rx(args).blockingIterable();
  }

}
