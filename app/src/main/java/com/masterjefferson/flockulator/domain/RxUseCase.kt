package com.masterjefferson.flockulator.domain

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */

open abstract class RxUseCase<A, R> {

    abstract fun rx(args: A?): Observable<R>

    fun rx(): Observable<R> {
        return rx(null)
    }

    open fun rxSingle(args: A?): Single<R> {
        return rx(args).take(1).singleOrError()
    }

    fun rxSingle(): Single<R> {
        return rx().take(1).singleOrError()
    }

    open fun rxCompletable(args: A?): Completable {
        return rxSingle(args).toCompletable()
    }

    fun rxCompletable(): Completable {
        return rxSingle().toCompletable()
    }

    fun blockingExecute(args: A?): Iterable<R> {
        return rx(args).blockingIterable()
    }

}
