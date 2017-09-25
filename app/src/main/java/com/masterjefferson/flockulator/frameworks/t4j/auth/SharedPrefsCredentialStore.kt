package com.masterjefferson.flockulator.frameworks.auth

import android.content.Context
import android.content.SharedPreferences

import com.masterjefferson.flockulator.R
import com.masterjefferson.flockulator.domain.twitter.auth.data.TwitterCredentialStore
import com.masterjefferson.flockulator.domain.twitter.auth.model.TwitterCredentials

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

class SharedPrefsCredentialStore(private val context: Context) : TwitterCredentialStore {

    override val consumerKey: String
        get() = context.getString(R.string.twitter_oauth_consumer_key)

    override val consumerSecret: String
        get() = context.getString(R.string.twitter_oauth_consumer_secret)

    override val credentials: TwitterCredentials?
        get() {
            val prefs = prefs
            val userId = prefs.getLong(KEY_USER_ID, -1)
            if (userId < 0) {
                return null
            }
            val token = prefs.getString(KEY_ACCESS_TOKEN, "")
            val secret = prefs.getString(KEY_ACCESS_SECRET, "")
            return TwitterCredentials(userId, token!!, secret!!)
        }

    private val prefs: SharedPreferences
        get() = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun storeCredentials(credentials: TwitterCredentials) {
        val editor = prefs.edit()
        editor.putLong(KEY_USER_ID, credentials.userId)
        editor.putString(KEY_ACCESS_TOKEN, credentials.accessToken)
        editor.putString(KEY_ACCESS_SECRET, credentials.tokenSecret)
        editor.commit()
    }

    companion object {

        private val PREFS_NAME = SharedPrefsCredentialStore::class.java.canonicalName
        private val KEY_USER_ID = PREFS_NAME + ".USER_ID"
        private val KEY_ACCESS_TOKEN = PREFS_NAME + ".ACCESS_TOKEN"
        private val KEY_ACCESS_SECRET = PREFS_NAME + ".ACCESS_SECRET"
    }
}
