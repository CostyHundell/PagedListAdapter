package com.thepeafactory.pagedlistadapterdemo.view_model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.costyhundell.nettypager.NettyItem
import com.thepeafactory.pagedlistadapterdemo.data_source.NewsArticleDataSource
import com.thepeafactory.pagedlistadapterdemo.data_source.NewsArticleDataSourceFactory

class NewsListViewModel:ViewModel(), LifecycleOwner {
    override fun getLifecycle(): Lifecycle = LifecycleRegistry(this)

    private var newsArticleDataSourceFactory =
        NewsArticleDataSourceFactory()

    private val config = PagedList.Config.Builder().apply {
        setEnablePlaceholders(true)
        setPageSize(NewsArticleDataSource.PAGE_SIZE)
    }.build()

    private var newsArticleList = LivePagedListBuilder<Int, NettyItem>(newsArticleDataSourceFactory, config).build()

    fun getNewsList() = newsArticleList
}