package com.thepeafactory.pagedlistadapterdemo

import com.costyhundell.nettypager.DeserializeResponse
import com.costyhundell.nettypager.NettyResponse
import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class BetAPIService {

    private val retrofitService: BetApi

    init {
        val adapter = DeserializeResponse(BetResponse::class.java)

        retrofitService = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().registerTypeAdapter(NettyResponse::class.java, adapter).create()))
            .baseUrl("https://api.the-odds-api.com/v2/")
            .build()
            .create(BetApi::class.java)
    }

    fun getService() = retrofitService

    interface BetApi {
        @GET("sports")
        fun getSport(
            @Query("apiKey") apiKey: String
        ): Single<NettyResponse>
    }
}