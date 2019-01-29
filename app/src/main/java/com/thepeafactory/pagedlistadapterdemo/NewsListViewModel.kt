package com.thepeafactory.pagedlistadapterdemo

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel

class NewsListViewModel:ViewModel(), LifecycleOwner {
    override fun getLifecycle(): Lifecycle = LifecycleRegistry(this)
}