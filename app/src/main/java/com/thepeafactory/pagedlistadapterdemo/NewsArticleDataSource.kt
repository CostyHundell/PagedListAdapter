package com.thepeafactory.pagedlistadapterdemo

import com.costyhundell.nettypager.NettyItem
import com.costyhundell.nettypager.NettyPagerDataSource
import com.costyhundell.nettypager.NettyResponse
import io.reactivex.Single
import java.util.*

class NewsArticleDataSource : NettyPagerDataSource(NUMBER_OF_APIS) {

    override var single =
        NewsAPIService().getService().getEverything(CATEGORY, PAGE_SIZE, FIRST_PAGE, BuildConfig.ApiKeyNews)

    var list = mutableListOf<NettyItem>()

    override fun onLoadInitialSuccess(callback: LoadInitialCallback<Int, NettyItem>, response: NettyResponse) {
        when (response.getResponseType()) {
            Constant.NEWS_RESPONSE -> {
                response as NewsResponse
                if (callsMade == 1) {
                    list = response.articles.toMutableList()
                } else {
                    list.addAll(response.articles)
                    list.shuffle()
                }
            }
            Constant.BET_RESPONSE -> {
                response as BetResponse
                list.addAll(response.data)
                list.shuffle()
            }
        }


        if (callsMade == NUMBER_OF_APIS) {
            postInitial(callback, list, PAGE_SIZE + 1)
        } else {
            setNextInitialCall(true, PAGE_SIZE, manageApis(), callback).runNext()
        }
    }

    override fun onLoadAfterSuccess(
        callback: LoadCallback<Int, NettyItem>,
        response: NettyResponse,
        params: LoadParams<Int>
    ) {
        when (response.getResponseType()) {
            Constant.NEWS_RESPONSE -> {
                response as NewsResponse
                if (callsMade == 1) {
                    list = response.articles.toMutableList()
                } else {
                    list.addAll(response.articles)
                    list.shuffle(Random())
                }
            }
        }

        postAfter(callback, list, params.key + 1)

    }

    override fun onLoadAfterError(error: Throwable) {
        error.printStackTrace()
        retry()
    }

    override fun onLoadInitialError(error: Throwable) {
        error.printStackTrace()
        retry()
    }

    override fun manageApis(): Single<NettyResponse> = when (callsMade) {
        1 -> BetAPIService().getService().getSport(BuildConfig.ApiKeyBet)
        else -> NewsAPIService().getService().getEverything(CATEGORY, PAGE_SIZE, FIRST_PAGE, BuildConfig.ApiKeyNews)
    }

    companion object {
        const val NUMBER_OF_APIS = 2
        const val PAGE_SIZE = 25
        private const val FIRST_PAGE = 1
        private const val CATEGORY = "sport"
    }
}