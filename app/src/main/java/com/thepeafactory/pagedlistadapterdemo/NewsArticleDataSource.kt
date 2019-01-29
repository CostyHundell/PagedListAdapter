package com.thepeafactory.pagedlistadapterdemo


class NewsArticleDataSource: FilterPageKeyedDataSource<NewsResponse, Int, NewsArticle>() {

    init {
        val service = NewsAPIService()
        setSubscriber(service.getService().getEverything(CATEGORY, PAGE_SIZE, FIRST_PAGE, BuildConfig.ApiKey))
    }

    override fun onLoadInitialDataSuccess(callback: LoadInitialCallback<Int, NewsArticle>, response: NewsResponse) {
        postInitial(callback, response.articles, PAGE_SIZE+1)
    }

    override fun onLoadAfterSuccess(callback: LoadCallback<Int, NewsArticle>, response: NewsResponse, params: LoadParams<Int>) {
        postAfter(callback, response.articles, params.key + 1)
    }


    companion object {
        const val PAGE_SIZE = 25
        private const val FIRST_PAGE = 1
        private const val CATEGORY = "sport"
    }
}