package com.system.traffic.remote.di

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.system.traffic.remote.BuildConfig
import com.system.traffic.remote.RemoteConstants
import com.system.traffic.remote.service.TrafficService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor { message ->
                            // 로그에서도 API 키 마스킹
                            Log.d("OkHttp", message.replace(oldValue = RemoteConstants.SERVICE_KEY, newValue = "***"))
                        }.setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                }
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideTrafficApi(
        okHttpClient: OkHttpClient, // Hilt로부터 OkHttpClient를 주입받음
        json: Json                  // Hilt로부터 Json을 주입받음
    ): TrafficService {
        return Retrofit.Builder()
            .baseUrl(RemoteConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(TrafficService::class.java)
    }
}