package com.thepeafactory.pagedlistadapterdemo

import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NewsAPIService {
    private val retrofitService: NewsApi

    init {
        retrofitService = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl("https://newsapi.org/v2/")
            .build()
            .create(NewsApi::class.java)
    }

    fun getService() = retrofitService

    interface NewsApi {
        @GET("everything")
        fun getEverything(
            @Query("q") category: String,
            @Query("pageSize") pageSize: Int,
            @Query("page") page: Int,
            @Query("apiKey") apiKey: String
        ): Single<NewsResponse>
    }
}