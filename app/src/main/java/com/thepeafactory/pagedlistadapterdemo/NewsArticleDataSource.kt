package com.thepeafactory.pagedlistadapterdemo

import com.costyhundell.nettypager.NettyItem
import com.costyhundell.nettypager.NettyPagerDataSource
import io.reactivex.Single
import java.util.*

class NewsArticleDataSource : NettyPagerDataSource<NewsResponse>(NUMBER_OF_APIS) {

    override var single =
        NewsAPIService().getService().getEverything(CATEGORY, PAGE_SIZE, FIRST_PAGE, BuildConfig.ApiKey)

    var list = mutableListOf<NettyItem>()

    override fun onLoadInitialSuccess(callback: LoadInitialCallback<Int, NettyItem>, response: NewsResponse) {
        if (callsMade == 1) {
            list = response.articles.toMutableList()
        } else {
            list.addAll(response.articles)
            list.shuffle(Random())
        }


        if (callsMade == NUMBER_OF_APIS) {
            postInitial(callback, list, PAGE_SIZE + 1)
        } else {
            setNextInitialCall(true, PAGE_SIZE, manageApis(), callback).runNext()
        }
    }

    override fun onLoadAfterSuccess(callback: LoadCallback<Int, NettyItem>, response: NewsResponse, params: LoadParams<Int>) {
        if (callsMade == 1) {
            list = response.articles.toMutableList()
        } else {
            list.addAll(response.articles)
            list.shuffle(Random())
        }

        if (callsMade == NUMBER_OF_APIS) {
            postAfter(callback, list, params.key + 1)
        } else {
            setNextAfterCall(PAGE_SIZE, params.key, manageApis(), callback).runNext()
        }
    }

    override fun onLoadAfterError(error: Throwable) {
        error.printStackTrace()
        retry()
    }

    override fun onLoadInitialError(error: Throwable) {
        error.printStackTrace()
        retry()
    }

    override fun manageApis(): Single<NewsResponse> = when (callsMade) {
        1 -> NewsAPIService().getService().getEverything("bitcoin", PAGE_SIZE, FIRST_PAGE, BuildConfig.ApiKey)
        2 -> NewsAPIService().getService().getEverything("apple", PAGE_SIZE, FIRST_PAGE, BuildConfig.ApiKey)
        3 -> NewsAPIService().getService().getEverything("android", PAGE_SIZE, FIRST_PAGE, BuildConfig.ApiKey)
        else -> NewsAPIService().getService().getEverything(CATEGORY, PAGE_SIZE, FIRST_PAGE, BuildConfig.ApiKey)
    }

    companion object {
        const val NUMBER_OF_APIS = 4
        const val PAGE_SIZE = 25
        private const val FIRST_PAGE = 1
        private const val CATEGORY = "sport"
    }
}