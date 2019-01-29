package com.costyhundell.nettypager

import androidx.paging.PageKeyedDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

abstract class FilterPageKeyedDataSource<T, Int, S> : PageKeyedDataSource<Int, S>() {

    var subscriber: Single<T>? = null
    var observable: Observable<T>? = null

    private var retryCompletable: Completable? = null
    private var compositeDisposable = CompositeDisposable()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, S>) {
        val disposable = when {
            subscriber != null -> {
                subscriber!!.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        setRetryAction(Action { loadInitial(params, callback) })
                        onLoadInitialSuccess(callback, response)
                    }, { error ->
                        onLoadInitialError(error)
                    })
            }
            observable != null -> {
                observable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        setRetryAction(Action { loadInitial(params, callback) })
                        onLoadInitialSuccess(callback, response)
                    }, { error ->
                        onLoadInitialError(error)
                    })
            }
            else -> null
        }

        compositeDisposable.add(disposable!!)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, S>) {
        val disposable = when {
            subscriber != null -> {
                subscriber!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        setRetryAction(Action { loadAfter(params, callback) })
                        onLoadAfterSuccess(callback, response, params)
                    }, { error ->
                        onLoadAfterError(error)
                    })
            }
            observable != null -> {
                observable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        setRetryAction(Action { loadAfter(params, callback) })
                        onLoadAfterSuccess(callback, response, params)
                    }, { error ->
                        onLoadAfterError(error)
                    })
            }
            else -> null
        }

        compositeDisposable.add(disposable!!)
    }

    fun postInitial(callback: LoadInitialCallback<Int, S>, items: List<S>, page: Int) {
        callback.onResult(items, null, page)
    }

    fun postAfter(callback: LoadCallback<Int, S>, items: List<S>, page: Int) {
        callback.onResult(items, page)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    fun clear() {
        compositeDisposable.clear()
    }

    private fun setRetryAction(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, S>) {
    }

    abstract fun onLoadInitialSuccess(callback: PageKeyedDataSource.LoadInitialCallback<Int, S>, response: T)
    abstract fun onLoadAfterSuccess(callback: LoadCallback<Int, S>, response: T, params: LoadParams<Int>)
    abstract fun onLoadInitialError(error: Throwable)
    abstract fun onLoadAfterError(error: Throwable)

}
