package com.masterjefferson.flockulator.onboard.authorize;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

public interface AuthorizeView {
  void openUrl(String url);
  void setCallbackUrl(String callbackUrl);
  void close();
}
