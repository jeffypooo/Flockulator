package com.masterjefferson.flockulator.onboard.welcome;

import com.masterjefferson.flockulator.domain.twitter.auth.AuthModule;

import dagger.Component;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
@Component(modules = {AuthModule.class})
public interface WelcomeComponent {

  WelcomePresenter presenter();

}
