package com.masterjefferson.flockulator.app.timber

import android.util.Log
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
class UnitTestTree : Timber.DebugTree() {

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    val date = SimpleDateFormat("hh:mm:ss:SSS").format(Date())
    if (t == null) {
      System.out.printf(
          TEST_LOG_FMT,
          date,
          Thread.currentThread().name,
          getLogChar(priority),
          tag,
          message
      )
      return
    }
    System.err.printf(
        TEST_LOG_FMT,
        date,
        Thread.currentThread().name,
        getLogChar(priority),
        tag,
        message
    )
  }

  private fun getLogChar(priority: Int): Char {
    when (priority) {
      Log.VERBOSE -> return 'V'
      Log.DEBUG   -> return 'D'
      Log.INFO    -> return 'I'
      Log.WARN    -> return 'W'
      Log.ERROR   -> return 'E'
      Log.ASSERT  -> return 'A'
      else        -> return '?'
    }
  }

  companion object {

    internal val TEST_LOG_FMT = "%s | %s %c/%s: %s\n"
    internal val TEST_LOG_FMT_THROWABLE = "%s | %s %c/%s %s\nError: %s\n"
  }
}