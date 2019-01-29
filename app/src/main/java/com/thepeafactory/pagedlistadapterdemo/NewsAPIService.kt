package com.thepeafactory.pagedlistadapterdemo

import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import retrofit2.http.POST
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
        @Headers("X-Api-Key: ${BuildConfig.ApiKey}")
        @POST("everything")
        fun getEverything(@Query("pageSize") pageSize: Int,
                          @Query("page") page: Int): Single<NewsResponse>
    }
}