package com.thepeafactory.pagedlistadapterdemo

import androidx.paging.PageKeyedDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsArticleDataSource: PageKeyedDataSource<Int, NewsArticle>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NewsArticle>) {
        val disposable = NewsAPIService().getService()
            .getEverything(PAGE_SIZE, FIRST_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                callback.onResult(response.articles, null, FIRST_PAGE + 1)
            }

        disposable.dispose()
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsArticle>) {
        val disposable = NewsAPIService().getService()
            .getEverything(PAGE_SIZE, FIRST_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                callback.onResult(response.articles, params.key + 1)
            }

        disposable.dispose()
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsArticle>) {
        // Nothing to load
    }

    companion object {
        private const val PAGE_SIZE = 25
        private const val FIRST_PAGE = 1
    }
}