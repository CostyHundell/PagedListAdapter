package com.thepeafactory.pagedlistadapterdemo.data_classes

import com.costyhundell.nettypager.NettyItem
import com.costyhundell.nettypager.NettyResponse
import com.google.gson.annotations.SerializedName
import com.thepeafactory.pagedlistadapterdemo.DemoConstants

data class NewsResponse(val status: String,
                        val totalResults: Int,
                        val articles: List<NewsArticle>) : NettyResponse {
    override fun getResponseType(): Int = DemoConstants.NEWS_RESPONSE

}

data class NewsArticle(val source: Source,
                       val author: String,
                       val title: String,
                       val description: String,
                       val url: String,
                       @SerializedName("urlToImage") val imageUrl: String,
                       val publishedAt: String,
                       val content: String): NettyItem {
    override fun getItemViewType(): Int = DemoConstants.NEWS_ARTICLE_TYPE
}

data class Source(val id: String?,
                  val name: String)
