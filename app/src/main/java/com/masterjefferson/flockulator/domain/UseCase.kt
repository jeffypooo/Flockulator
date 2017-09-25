package com.masterjefferson.flockulator.domain

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

abstract class UseCase<A, R> {
    abstract fun run(args: A?): R?

    fun run(): R? {
        return run(null)
    }
}
