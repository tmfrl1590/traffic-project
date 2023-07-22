package com.system.traffic.network


import com.system.traffic.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = "http://api.gwangju.go.kr/json/"
    private const val SERVICE_KEY = "iY5zhbUCDE1IXhGGBdQSYlo6HS3j1OB%2FBBm5Y%2F81UpVnU2nCbtWADPSJXbJwmWyAM8DdfIlkvMaEHao5Sbs4%2Fg%3D%3D"


    private fun createClient() : OkHttpClient{
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.SECONDS)
            .readTimeout(1000, TimeUnit.SECONDS)
            .writeTimeout(1000, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }
    private val client = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createClient())
        .build()

    fun getInstance() : Retrofit {
        return client
    }

    fun getServiceKey() : String {
        return SERVICE_KEY
    }
}