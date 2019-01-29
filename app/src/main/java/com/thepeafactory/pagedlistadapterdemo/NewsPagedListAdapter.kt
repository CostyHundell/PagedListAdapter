package com.thepeafactory.pagedlistadapterdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.news_article.view.*

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
        private val title = itemView.title
        private val description = itemView.description
        fun bind(item: NewsArticle) {
            title.text = item.title
            description.text = item.description
        }
    }
}