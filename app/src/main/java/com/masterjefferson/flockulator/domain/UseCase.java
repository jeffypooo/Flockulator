package com.masterjefferson.flockulator.domain;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public abstract class UseCase<A, R> {
  public abstract R run(A args);

  public R run() {
    return run(null);
  }
}
