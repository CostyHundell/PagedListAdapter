package com.thepeafactory.pagedlistadapterdemo

import com.costyhundell.nettypager.NettyPagerDataSource


class NewsArticleDataSource : NettyPagerDataSource<NewsResponse, Int, NewsArticle>() {

    init {
        subscriber = NewsAPIService().getService().getEverything(CATEGORY, PAGE_SIZE, FIRST_PAGE, BuildConfig.ApiKey)
    }

    override fun onLoadInitialSuccess(callback: LoadInitialCallback<Int, NewsArticle>, response: NewsResponse) {
        val list = response.articles.filter {
            it.source.name == "Lifehacker.com"
        }
        postInitial(callback, list, PAGE_SIZE + 1)
    }

    override fun onLoadAfterSuccess(callback: LoadCallback<Int, NewsArticle>, response: NewsResponse, params: LoadParams<Int>) {
        postAfter(callback, response.articles, params.key + 1)
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