package com.thepeafactory.pagedlistadapterdemo

import com.costyhundell.nettypager.NettyItem

data class OddsResponse(val odds: List<Odds>)

data class Odds(val bet: String) : NettyItem