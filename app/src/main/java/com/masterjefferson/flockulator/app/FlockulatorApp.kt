package com.masterjefferson.flockulator.app

import android.app.Application
import io.realm.Realm
import timber.log.Timber

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

class FlockulatorApp : Application() {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Realm.init(this)
    }

    fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            createAppComponent()
        }
        return appComponent!!
    }

    private fun createAppComponent() {
        this.appComponent = DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .build()
    }
}
