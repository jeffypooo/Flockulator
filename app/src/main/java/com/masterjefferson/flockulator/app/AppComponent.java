package com.masterjefferson.flockulator.app;

import com.masterjefferson.flockulator.frameworks.AndroidModule;
import com.masterjefferson.flockulator.frameworks.T4JModule;
import com.masterjefferson.flockulator.main.RootActivity;
import com.masterjefferson.flockulator.onboard.authorize.AuthorizeActivity;
import com.masterjefferson.flockulator.onboard.welcome.WelcomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
@Singleton
@Component(modules = {ContextModule.class, T4JModule.class, AndroidModule.class})
public interface AppComponent {

  void inject(WelcomeActivity welcomeActivity);
  void inject(AuthorizeActivity authorizeActivity);
  void inject(RootActivity rootActivity);

}
