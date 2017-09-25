package com.masterjefferson.flockulator.app

import com.masterjefferson.flockulator.frameworks.AndroidModule
import com.masterjefferson.flockulator.frameworks.realm.RealmModule
import com.masterjefferson.flockulator.frameworks.t4j.T4JModule
import com.masterjefferson.flockulator.main.stream.RootActivity
import com.masterjefferson.flockulator.onboard.authorize.AuthorizeActivity
import com.masterjefferson.flockulator.onboard.welcome.WelcomeActivity
import dagger.Component
import javax.inject.Singleton

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
@Singleton
@Component(
    modules = arrayOf(
        ContextModule::class,
        RealmModule::class,
        T4JModule::class,
        AndroidModule::class
    )
)
interface AppComponent {

  fun inject(welcomeActivity: WelcomeActivity)
  fun inject(authorizeActivity: AuthorizeActivity)
  fun inject(rootActivity: RootActivity)
}
