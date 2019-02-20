package com.thepeafactory.pagedlistadapterdemo

import com.costyhundell.nettypager.NettyItem
import com.costyhundell.nettypager.NettyPagerDataSource


class NewsArticleDataSource : NettyPagerDataSource<NewsResponse>() {

    override var single = NewsAPIService().getService().getEverything(CATEGORY, PAGE_SIZE, FIRST_PAGE, BuildConfig.ApiKey)

    override fun onLoadInitialSuccess(callback: LoadInitialCallback<Int, NettyItem>, response: NewsResponse) {

        val list = response.articles.mapIndexed { index, newsArticle ->
            if (index % 5 == 0)
                Odds("Bet365")
            else
                newsArticle
        }

        postInitial(callback, list, PAGE_SIZE + 1)
    }

    override fun onLoadAfterSuccess(callback: LoadCallback<Int, NettyItem>, response: NewsResponse, params: LoadParams<Int>) {
        val list = response.articles.mapIndexed { index, newsArticle ->
            if (index % 5 == 0)
                Odds("Bet365")
            else
                newsArticle
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

    companion object {
        const val PAGE_SIZE = 25
        private const val FIRST_PAGE = 1
        private const val CATEGORY = "sport"
    }
}