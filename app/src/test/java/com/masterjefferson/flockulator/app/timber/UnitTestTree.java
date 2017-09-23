package com.masterjefferson.flockulator.app.timber;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

public class UnitTestTree
    extends Timber.DebugTree {

  static final String TEST_LOG_FMT           = "%s | %s %c/%s: %s\n";
  static final String TEST_LOG_FMT_THROWABLE = "%s | %s %c/%s %s\nError: %s\n";

  @Override
  protected void log(int priority, String tag, String message, Throwable t) {
    String date = new SimpleDateFormat("hh:mm:ss:SSS").format(new Date());
    if (t == null) {
      System.out.printf(
          TEST_LOG_FMT,
          date,
          Thread.currentThread().getName(),
          getLogChar(priority),
          tag,
          message
      );
      return;
    }
    System.err.printf(
        TEST_LOG_FMT,
        date,
        Thread.currentThread().getName(),
        getLogChar(priority),
        tag,
        message
    );
  }

  private char getLogChar(int priority) {
    switch (priority) {
      case Log.VERBOSE:
        return 'V';
      case Log.DEBUG:
        return 'D';
      case Log.INFO:
        return 'I';
      case Log.WARN:
        return 'W';
      case Log.ERROR:
        return 'E';
      case Log.ASSERT:
        return 'A';
      default:
        return '?';
    }
  }
}