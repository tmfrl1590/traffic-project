package com.system.traffic.common

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Constants {
    const val BASE_URL = "http://api.gwangju.go.kr/json/"
    const val SERVICE_KEY = "iY5zhbUCDE1IXhGGBdQSYlo6HS3j1OB%2FBBm5Y%2F81UpVnU2nCbtWADPSJXbJwmWyAM8DdfIlkvMaEHao5Sbs4%2Fg%3D%3D"

    const val TRAFFIC_DATABASE_NAME = "traffic_database.db"
    const val STATION_ENTITY = "station_entity"
    const val LINE_ENTITY = "line_entity"
    const val STATION_LIKE_ENTITY = "station_like_entity"
    const val LINE_LIKE_ENTITY = "line_like_entity"
}