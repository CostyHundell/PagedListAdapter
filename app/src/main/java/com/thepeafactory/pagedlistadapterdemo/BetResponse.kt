package com.thepeafactory.pagedlistadapterdemo

import com.costyhundell.nettypager.NettyItem
import com.costyhundell.nettypager.NettyResponse
import com.google.gson.annotations.SerializedName

data class BetResponse(val succes: Boolean, val data: List<Sport>) : NettyResponse {
    override fun getResponseType(): Int = Constant.BET_RESPONSE
}

data class Sport(
    @SerializedName("sport") val compName: String,
    @SerializedName("sport_group") val name: String,
    @SerializedName("display_name") val display: String
) : NettyItem {
    override fun getItemViewType(): Int = Constant.SPORT_TYPE
}