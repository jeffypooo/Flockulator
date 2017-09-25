package com.masterjefferson.flockulator.onboard.authorize

import com.masterjefferson.flockulator.domain.twitter.auth.AuthModule

import javax.inject.Singleton

import dagger.Component

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */
@Singleton
@Component(modules = arrayOf(AuthModule::class))
interface AuthorizeComponent {

    fun presenter(): AuthorizePresenter

}
