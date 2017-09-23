package com.masterjefferson.flockulator.onboard.welcome;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public interface WelcomeView {

  void openAuthorizationPage(String url);
  void setLoading(boolean loading);
  void proceedToApplication();
}
