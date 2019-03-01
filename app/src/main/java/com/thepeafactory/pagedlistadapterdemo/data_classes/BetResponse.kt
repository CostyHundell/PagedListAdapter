package com.thepeafactory.pagedlistadapterdemo.data_classes

import com.costyhundell.nettypager.NettyItem
import com.costyhundell.nettypager.NettyResponse
import com.google.gson.annotations.SerializedName
import com.thepeafactory.pagedlistadapterdemo.DemoConstants

data class BetResponse(val succes: Boolean, val data: List<Sport>) : NettyResponse {
    override fun getResponseType(): Int = DemoConstants.BET_RESPONSE
}

data class Sport(
    @SerializedName("sport") val compName: String,
    @SerializedName("sport_group") val name: String,
    @SerializedName("display_name") val display: String
) : NettyItem {
    override fun getItemViewType(): Int = DemoConstants.SPORT_TYPE
}