package com.masterjefferson.flockulator.app

import timber.log.Timber

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */

abstract class FlockulatorActivityPresenter<V> {

    protected var view: V? = null

    open fun onCreate(view: V) {
        this.view = view
    }

    open fun onResume() {

    }

    open fun onDestroy() {
        this.view = null
    }

    protected fun warnViewNull(methodName: String) {
        Timber.w("%s: view is null", methodName)
    }

    protected fun logViewAction(fmt: String, vararg args: Any) {
        val msg = String.format(fmt, *args)
        Timber.d("view - %s", msg)
    }

}
