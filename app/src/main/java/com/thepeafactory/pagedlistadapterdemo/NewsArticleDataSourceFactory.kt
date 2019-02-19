package com.thepeafactory.pagedlistadapterdemo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.DataSource.Factory
import androidx.paging.PageKeyedDataSource
import com.costyhundell.nettypager.NettyItem

class NewsArticleDataSourceFactory : Factory<Int, NettyItem>() {
    private var newsArticleLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, NettyItem>> = MutableLiveData()
    private var newsDataSource = NewsArticleDataSource()
    override fun create(): DataSource<Int, NettyItem> {
        if (newsDataSource.isInvalid) newsDataSource = NewsArticleDataSource()
        newsArticleLiveDataSource.postValue(newsDataSource)
        return newsDataSource
    }

    fun getDataSource() = newsDataSource
}