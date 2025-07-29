package com.traffic.common

object CommonConfig {
    var adUnitId: String = ""

    fun initialize(adUnitId: String){
        this.adUnitId = adUnitId
    }
}