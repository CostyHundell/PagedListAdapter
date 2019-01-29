package com.thepeafactory.pagedlistadapterdemo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.DataSource.Factory
import androidx.paging.PageKeyedDataSource

class NewsArticleDataSourceFactory : Factory<Int, NewsArticle>() {
    private var newsArticleLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, NewsArticle>> = MutableLiveData()
    private var newsDataSource = NewsArticleDataSource()
    override fun create(): DataSource<Int, NewsArticle> {
        if (newsDataSource.isInvalid) newsDataSource = NewsArticleDataSource()
        newsArticleLiveDataSource.postValue(newsDataSource)
        return newsDataSource
    }

    fun getDataSource() = newsDataSource
}