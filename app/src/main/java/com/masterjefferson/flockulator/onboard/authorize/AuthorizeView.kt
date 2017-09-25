package com.masterjefferson.flockulator.onboard.authorize

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

interface AuthorizeView {
    fun openUrl(url: String)
    fun setCallbackUrl(callbackUrl: String)
    fun close()
}
