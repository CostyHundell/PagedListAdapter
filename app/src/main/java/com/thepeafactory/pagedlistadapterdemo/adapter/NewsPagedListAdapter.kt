package com.thepeafactory.pagedlistadapterdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.costyhundell.nettypager.NettyItem
import com.costyhundell.nettypager.NettyPagedListAdapter
import com.thepeafactory.pagedlistadapterdemo.DemoConstants
import com.thepeafactory.pagedlistadapterdemo.R
import com.thepeafactory.pagedlistadapterdemo.data_classes.NewsArticle
import com.thepeafactory.pagedlistadapterdemo.data_classes.Sport
import kotlinx.android.synthetic.main.news_article.view.*

class NewsPagedListAdapter: NettyPagedListAdapter<RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        DemoConstants.NEWS_ARTICLE_TYPE -> ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.news_article, parent, false))
        else -> OddsHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_article, parent, false))
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (item) {
            is NewsArticle -> {
                holder as ViewHolder
                holder.bind(item)
            }
            is Sport -> {
                holder as OddsHolder
                holder.bind(item)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NettyItem>() {
            override fun areItemsTheSame(oldItem: NettyItem, newItem: NettyItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NettyItem, newItem: NettyItem): Boolean {
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

    inner class OddsHolder(view: View): RecyclerView.ViewHolder(view) {
        private val title = itemView.title
        private val description = itemView.description
        fun bind(item: Sport) {
            title.text = item.display
            description.text = item.name
        }
    }
}