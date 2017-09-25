package com.masterjefferson.flockulator.app

import android.content.Context

import dagger.Module
import dagger.Provides

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
@Module
class ContextModule(private val context: Context) {

    @Provides internal fun provideAppContext(): Context {
        return context
    }

}
