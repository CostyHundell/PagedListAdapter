package com.thepeafactory.pagedlistadapterdemo

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class NewsListViewModel:ViewModel(), LifecycleOwner {
    override fun getLifecycle(): Lifecycle = LifecycleRegistry(this)

    private var newsArticleDataSourceFactory = NewsArticleDataSourceFactory()

    private val config = PagedList.Config.Builder().apply {
        setEnablePlaceholders(false)
        setPageSize(NewsArticleDataSource.PAGE_SIZE)
    }.build()

    private var newsArticleList = LivePagedListBuilder<Int, NewsArticle>(newsArticleDataSourceFactory, config).build()

    fun getNewsList() = newsArticleList
}