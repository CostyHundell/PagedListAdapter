package com.thepeafactory.pagedlistadapterdemo

import com.costyhundell.nettypager.NettyItem
import com.costyhundell.nettypager.NettyResponse
import com.google.gson.annotations.SerializedName

data class NewsResponse(val status: String,
                        val totalResults: Int,
                        val articles: List<NewsArticle>) : NettyResponse {
    override fun getResponseType(): Int = Constant.NEWS_RESPONSE

}

data class NewsArticle(val source: Source,
                        val author: String,
                        val title: String,
                        val description: String,
                        val url: String,
                        @SerializedName("urlToImage") val imageUrl: String,
                        val publishedAt: String,
                        val content: String): NettyItem {
    override fun getItemViewType(): Int = Constant.NEWS_ARTICLE_TYPE
}

data class Source(val id: String?,
                  val name: String)
