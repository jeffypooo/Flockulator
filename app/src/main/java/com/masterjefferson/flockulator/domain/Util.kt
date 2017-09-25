package com.masterjefferson.flockulator.domain

import timber.log.Timber

/**
 * ${FILE_NAME}
 * Created by jeff on 9/25/17.
 */
fun timeBlock(label: String, block: () -> Unit) {
  val start = System.currentTimeMillis()
  block()
  Timber.v("$label duration - ${System.currentTimeMillis() - start} ms")
}