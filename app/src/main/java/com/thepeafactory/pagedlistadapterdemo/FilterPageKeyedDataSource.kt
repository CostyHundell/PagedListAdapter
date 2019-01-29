package com.thepeafactory.pagedlistadapterdemo

import androidx.paging.PageKeyedDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class FilterPageKeyedDataSource<T, Int, S> : PageKeyedDataSource<Int, S>() {

    private var subscriber: Single<T>? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, S>) {
       val disposable = subscriber!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ response ->
                onLoadInitialDataSuccess(callback, response)
            }, { error ->
                error.printStackTrace()
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, S>) {
        val disposable = subscriber!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                onLoadAfterSuccess(callback, response, params)
            }

    }

    fun postInitial(callback: LoadInitialCallback<Int, S>, items: List<S>, page: Int) {
        callback.onResult(items, null, page)
    }

    fun postAfter( callback: LoadCallback<Int, S>, items: List<S>, page:Int) {
        callback.onResult(items, page)
    }

    fun setSubscriber(subscriber: Single<T>) {
        this.subscriber = subscriber
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, S>) {
    }


    abstract fun onLoadInitialDataSuccess(callback: PageKeyedDataSource.LoadInitialCallback<Int, S>, response: T)
    abstract fun onLoadAfterSuccess(callback: LoadCallback<Int, S>, response: T, params: LoadParams<Int>)

}
