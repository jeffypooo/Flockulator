package com.masterjefferson.flockulator.app;

import timber.log.Timber;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public abstract class FlockulatorActivityPresenter<V> {

  protected V view;

  public void onCreate(V view) {
    this.view = view;
  }

  public void onResume() {

  }

  public void onDestroy() {
    this.view = null;
  }

  protected void warnViewNull(String methodName) {
    Timber.w("%s: view is null", methodName);
  }

  protected void logViewAction(String fmt, Object... args) {
    String msg = String.format(fmt, args);
    Timber.d("view - %s", msg);
  }

}
