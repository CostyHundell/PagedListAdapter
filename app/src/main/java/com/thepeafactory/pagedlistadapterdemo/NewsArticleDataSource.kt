package com.thepeafactory.pagedlistadapterdemo

import androidx.paging.PageKeyedDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsArticleDataSource: PageKeyedDataSource<Int, NewsArticle>() {
    private val service = NewsAPIService()
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NewsArticle>) {
        val disposable = service.getService()
            .getEverything(CATEGORY, PAGE_SIZE, FIRST_PAGE, BuildConfig.ApiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ response ->
                callback.onResult(response.articles, null, FIRST_PAGE + 1)
            }, { error ->
                error.printStackTrace()
            })

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsArticle>) {
        val disposable = NewsAPIService().getService()
            .getEverything(CATEGORY, PAGE_SIZE, params.key, BuildConfig.ApiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                callback.onResult(response.articles, params.key + 1)
            }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsArticle>) {
        // Nothing to load before as we're only loading afterwards
    }

    companion object {
        const val PAGE_SIZE = 25
        private const val FIRST_PAGE = 1
        private const val CATEGORY = "sport"
    }
}