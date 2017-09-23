package com.masterjefferson.flockulator.app;

import android.app.Application;

import timber.log.Timber;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public class FlockulatorApp
    extends Application {

  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
  }

  public AppComponent getAppComponent() {
    if (appComponent == null) {
      createAppComponent();
    }
    return appComponent;
  }

  private void createAppComponent() {
    this.appComponent = DaggerAppComponent.builder()
        .contextModule(new ContextModule(this))
        .build();
  }
}
