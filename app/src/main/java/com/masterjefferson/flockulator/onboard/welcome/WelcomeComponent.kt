package com.masterjefferson.flockulator.onboard.welcome

import com.masterjefferson.flockulator.domain.twitter.auth.AuthModule

import dagger.Component

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
@Component(modules = arrayOf(AuthModule::class))
interface WelcomeComponent {

    fun presenter(): WelcomePresenter

}
