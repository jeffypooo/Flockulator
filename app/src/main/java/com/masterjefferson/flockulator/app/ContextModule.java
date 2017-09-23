package com.masterjefferson.flockulator.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
@Module
public class ContextModule {

  private final Context context;

  public ContextModule(Context context) {
    this.context = context;
  }

  @Provides Context provideAppContext() {
    return context;
  }

}
