package com.masterjefferson.flockulator.onboard.welcome

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

interface WelcomeView {

    fun openAuthorizationPage(url: String)
    fun setLoading(loading: Boolean)
    fun proceedToApplication()
}
