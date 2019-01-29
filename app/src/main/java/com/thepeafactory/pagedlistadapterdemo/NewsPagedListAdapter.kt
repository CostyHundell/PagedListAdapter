package com.thepeafactory.pagedlistadapterdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class NewsPagedListAdapter: PagedListAdapter<NewsArticle, NewsPagedListAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_article, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsArticle>() {
            override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: NewsArticle) {}
    }
}